#!/usr/bin/env python

import sys

# File format is taken to be well defined, error free
# ------
#  1
#  ABC
#  2
#  BA
#  AB
#  0

def read_ballots(infile):
    ballots = []
    count = int(infile.readline())
    if count == 0: return ballots # empty array is sentinel for end-of-file
    for i in xrange(count):
        ballot = infile.readline().strip()
        ballot = [ch for ch in ballot] # easier to process as w/r ['A', 'B'] than as immutable 'AB'
        ballots.append(ballot)
    return ballots

def print_candidates_being_eliminated(candidates):
    print '%s ->' % ''.join(sorted([ch for ch in candidates])),
    
def eliminate_those_without_first_place_votes(ballots):
    candidates_with_first_place_votes = set()
    candidates_without_first_place_votes = set()
    for ballot in ballots:
        candidates_with_first_place_votes.add(ballot[0])
    for ballot in ballots:
        for candidate in reversed(ballot):
            if candidate not in candidates_with_first_place_votes:
                candidates_without_first_place_votes.add(candidate)
                ballot.remove(candidate)
    if len(candidates_without_first_place_votes) > 0:
        print_candidates_being_eliminated(candidates_without_first_place_votes)
    
def count_first_place_votes(ballots):
    contenders = {}
    first_place_vote_recipients = map(lambda ballot: ballot[0], ballots)
    for first_place_vote_recipient in first_place_vote_recipients:
        contenders[first_place_vote_recipient] = 0
    for first_place_vote_recipient in first_place_vote_recipients:
        contenders[first_place_vote_recipient] = contenders[first_place_vote_recipient] + 1
    return contenders

def analyze_statistics(statistics, num_remaining_ballots):
    for candidate, num_first_place_votes in statistics.items():
        if 2 * num_first_place_votes > num_remaining_ballots: # if strict majority
            return True, candidate
    return False, None

def identify_least_popular(statistics, num_remaining_ballots):
    lowest_first_place_vote_count = num_remaining_ballots
    least_popular_candidates = set()
    for candidate, num_first_place_votes in statistics.items():
        if num_first_place_votes < lowest_first_place_vote_count:
            lowest_first_place_vote_count = num_first_place_votes
            least_popular_candidates.clear()
        if num_first_place_votes <= lowest_first_place_vote_count:
            least_popular_candidates.add(candidate)
    return least_popular_candidates

def eliminate_least_popular_candidates(ballots, least_popular_candidates):
    print_candidates_being_eliminated(least_popular_candidates)
    for ballot in ballots:
        for candidate in reversed(ballot):
            if candidate in least_popular_candidates:
                ballot.remove(candidate)
    retained_ballots = []
    for ballot in ballots:
        if len(ballot) > 0:
            retained_ballots.append(ballot)
    return retained_ballots

def process_ballots(ballots):
    is_first_round = True
    while True:
        num_remaining_ballots = len(ballots)
        if num_remaining_ballots == 0:
            print 'no winner'
            break
        statistics = count_first_place_votes(ballots)
        winner_declared, winner = analyze_statistics(statistics, num_remaining_ballots)
        if winner_declared:
            print '%s' % winner
            break
        if is_first_round: # the first round (and only the first round) might contain candidates with no rank-one votes
            eliminate_those_without_first_place_votes(ballots)
            is_first_round = False # by design, this scenario should never present itself again
        least_popular_candidates = identify_least_popular(statistics, num_remaining_ballots)
        ballots = eliminate_least_popular_candidates(ballots, least_popular_candidates)

if __name__ == '__main__':
    t = int(sys.stdin.readline())
    for i in xrange(t):
        ballots = read_ballots(sys.stdin)
        if len(ballots) == 0: break
        process_ballots(ballots)

