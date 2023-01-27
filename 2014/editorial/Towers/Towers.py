#!/usr/bin/env python

import sys
from copy import copy
from towers_file_read import read_puzzle
from towers_count import count_visible_towers
from towers_permutations import cache_permutations

def solution_contains_bad_entries(solution):
    dimension = len(solution)
    for row in range(dimension):
        for column in range(dimension):
            if not 0 <= solution[row][column] <= 9:
                return True
    return False

def rows_contain_duplicates(solution):
    dimension = len(solution)
    for column in range(dimension):
        seen = set()
        for row in range(dimension):
            height = solution[row][column]
            if height == 0: continue
            if height in seen: return True
            seen.add(height)
    return False

def transpose_solution(solution):
    dimension = len(solution)
    transpose = []
    for column in range(dimension):
        transpose.append(map(lambda row: row[column], solution))
    return transpose

def transpose_constraints(constraints):
    return {
        'west': constraints['north'],
        'north': constraints['west'],
        'east': constraints['south'],
        'south': constraints['east'] 
    }

def constraint_is_violated(row, num_visible_needed):
    if 0 in row or num_visible_needed == 0: return False
    return count_visible_towers(row) != num_visible_needed        

def constraints_are_violated(solution, constraints, forward_key, backward_key):
    dimension = len(solution)
    for row in range(dimension):
        if constraint_is_violated(solution[row], constraints[forward_key][row]): return True
        if constraint_is_violated(solution[row][::-1], constraints[backward_key][row]): return True
    return False

def solution_is_bogus(solution, constraints):
    if solution_contains_bad_entries(solution): return True
    if rows_contain_duplicates(solution): return True
    if rows_contain_duplicates(transpose_solution(solution)): return True
    if constraints_are_violated(solution, constraints, 'west', 'east'): return True
    if constraints_are_violated(transpose_solution(solution), constraints, 'north', 'south'): return True
    return False

def solution_is_complete(solution):
    dimension = len(solution)
    for row in range(dimension):
        for column in range(dimension):
            if solution[row][column] == 0: 
                return False
    return True

def row_is_complete(row):
    for value in row:
        if value == 0: return False
    return True

def option_is_worth_considering(row, option):
    dimension = len(row)
    for column in range(dimension):
        if row[column] > 0 and row[column] != option[column]: return False
    return True

def find_most_constrained_row(solution, constraints, cache):
    most_constrained_row = -1
    most_constrained_options = []
    dimension = len(solution)
    for row in range(dimension):
        if not row_is_complete(solution[row]):
            key = (constraints['west'][row], constraints['east'][row])
            options = [option for option in cache[key] if option_is_worth_considering(solution[row], option)]
            if most_constrained_row == -1 or len(options) < len(most_constrained_options):
                most_constrained_row = row
                most_constrained_options = options
    return most_constrained_row, most_constrained_options

def compute_row_based_singletons(solution, constraints, cache):
    dimension = len(solution)
    row_based_options = {}
    for row in range(dimension):
        key = (constraints['west'][row], constraints['east'][row])
        options = [option for option in cache[key] if option_is_worth_considering(solution[row], option)]        
        for column in range(dimension):
            row_based_options[(row, column)] = set()
            for option in options:
                row_based_options[(row, column)].add(option[column])
    return row_based_options

def find_constrained_points(solution, constraints, cache):
    dimension = len(solution)
    row_based_singletons = compute_row_based_singletons(solution, constraints, cache)
    column_based_singletons = compute_row_based_singletons(transpose_solution(solution), transpose_constraints(constraints), cache)
    constrained_points = {}
    for row in range(dimension):
        for column in range(dimension):
            if solution[row][column] == 0:
                possibilities = row_based_singletons[(row, column)] & column_based_singletons[(column, row)]
                if len(possibilities) == 1:
                    constrained_points[(row, column)] = possibilities.pop()
    return constrained_points

def fill_in_singletons(solution, constraints, cache):
    updated_coords = set()
    constrained_coords = find_constrained_points(solution, constraints, cache)
    for (row, column) in constrained_coords.keys():
        solution[row][column] = constrained_coords[(row, column)]
        updated_coords.add((row, column))
    return updated_coords

def clear_singletons(solution, updated_coords):
    for row, column in updated_coords:
        solution[row][column] = 0

def solve(solution, constraints, cache, level = 0):
    if solution_is_bogus(solution, constraints): return False
    if solution_is_complete(solution): return True

    # updated_coords = fill_in_singletons(solution, constraints, cache)

    most_constrained_row, most_constrained_row_options = find_most_constrained_row(solution, constraints, cache)
    most_constrained_column, most_constrained_column_options = find_most_constrained_row(transpose_solution(solution), transpose_constraints(constraints), cache)
    should_flip = most_constrained_column != -1 and len(most_constrained_column_options) < len(most_constrained_row_options)
    row = most_constrained_column if should_flip else most_constrained_row
    options = most_constrained_column_options if should_flip else most_constrained_row_options
    if len(options) == 0:
        # clear_singletons(solution, updated_coords)
        return False

    working_solution = transpose_solution(solution) if should_flip else copy(solution)
    working_constraints = transpose_constraints(constraints) if should_flip else copy(constraints)
    impacted_columns = [column for column in range(len(working_solution)) if working_solution[row][column] == 0]
    solved = False
    
    for option in options:
        for column in impacted_columns: working_solution[row][column] = option[column]
        if solve(working_solution, working_constraints, cache, level + 1): solved = True
        if solved: break
        for column in impacted_columns: working_solution[row][column] = 0
    
    if should_flip: working_solution = transpose_solution(working_solution)
    # if not solved: clear_singletons(working_solution, updated_coords)
    for row in range(len(working_solution)): solution[row] = working_solution[row]
    return solved

def print_solution(solution):
    dimension = len(solution)
    for row in range(0, dimension):
        for column in range(0, len(solution[row])):
            sys.stdout.write('%s' % solution[row][column])
        sys.stdout.write('\n')
    sys.stdout.write('\n')

def solve_puzzles():
    t = int(sys.stdin.readline())
    for t in xrange(t):
        solution, constraints = read_puzzle(sys.stdin);
        dimension = len(solution)
        cache = cache_permutations(dimension)
        if not solve(solution, constraints, cache):
            sys.stdout.write('no\n\n')
        else:
            print_solution(solution)

if __name__ == "__main__":
    solve_puzzles()
