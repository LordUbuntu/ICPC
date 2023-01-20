#!python3

def hex_to_binary_string(hex_string, binary_length):
    bin_string = ""
    # since these hex strings are pretty long 
    # (and the binary strings will be longer),
    # do this one hex 'digit' at a time
    for dig in hex_string:
        # each hex digit gives us 4 binary digits
        bin_string += bin(int(dig, 16))[2:].zfill(4)  

    # pad 0s at the left to make string k*4 bits long
    bin_string = bin_string.zfill(binary_length)

    return bin_string

def xor_bitstrings(a, b, binary_length):
    res = ""
    for i in range(binary_length):
        if (a[i] == '1' and b[i] == '0') or (a[i] == '0' and b[i] == '1'):
            res += '1'
        else:
            res += '0'

    return res


# read sizes
n, m, k = map(int, input().split())

num_bits = 4*k      # number of bits in each bitstring

# store b sequence based on the first set bit in each of them
b_sequence = {}
# b_sequence[j] = (b_i, i)
# means bitstring b_i is the earliest occurring bitstring in 
# sequence b_1, b_2, ..., b_n with bit j set

# read sequence b, and convert to binary string representations
for i in range(n):
    # read and convert
    new_b = input().strip()
    new_b = hex_to_binary_string(new_b, num_bits)

    # find the position of the first 1 in this bitstring
    first_set = new_b.find('1')

    # if this bitstring is all 0s, and we haven't had that yet,
    # add to basis set
    # if first_set == -1 and first_set not in b_sequence:
    #     b_sequence[first_set] = (new_b, i)
    #     continue

    # we want each b_1, b_2, ..., b_i sequence to be a basis
    # ie, they should all be independent, and each element
    # can be expressed as a linear combination of other elements

    # if this is b_1 (first bitstring), just add it to data structure
    # if this string is all 0s, and we don't have that already, add it
    if len(b_sequence) == 0 or (first_set == -1 and first_set not in b_sequence):
        b_sequence[first_set] = (new_b, i)
        continue

    # check this new bitstring against the existing ones,
    # and figure out if this bitstring gives us any additional power
    added = False

    # check against all our stored bitstrings in order of earliest bit
    for j in range(num_bits):
        # if first_set of new bitstring not equal to j, continue
        if first_set != j:
            continue

        # if we have no bitstrings stored with the same first_set as this
        # new bitstring, we definitely want this
        # so add it to the data structure
        if first_set not in b_sequence:
            b_sequence[first_set] = (new_b, i)
            added = True
            break

        # current loop sequence bitstring and the new one have first 1s in the
        # same spot, so xor the binaries together
        # this modifies new_b without losing any future representative power
        new_b = xor_bitstrings(new_b, b_sequence[j][0], num_bits)
        # and update the first_set to match modified bitstring
        first_set = new_b.find('1')

    # if we make it all the way through and new_b is not all 0s, add
    # it to our tracking
    if added == False and first_set != -1:
        b_sequence[first_set] = (new_b, i)
    # if it is all 0s, and we don't have that yet, save it
    elif added == False and first_set == -1 and first_set not in b_sequence:
        b_sequence[first_set] = (new_b, i)


# read and process each item in a sequence
for i in range(m):
    # read and convert
    curr_a = input().strip()
    curr_a = hex_to_binary_string(curr_a, num_bits)

    # find the first '1' in this bitstring
    first_set = curr_a.find('1')

    # if there are no 1s in this bitstring (all 0), can only build it if 

    # based on our basis set of the b_i bitstrings, can we get a_i as a combination?

    # if the first '1' in a_i is not set in any of the '1's in the b_i bitstrings,
    # there is no way to represent a_i
    if first_set not in b_sequence:
        print(-1)
        continue

    # otherwise, start building a combination of the b_i bitstrings
    # and track the biggest index we must use
    solved = False
    max_b = -1
    for j in range(-1, num_bits):
        # this isn't a bit we need for a_i, move to next
        if first_set != j:
            continue

        # no way to get this bit from the b_i bitstrings, fail
        if first_set not in b_sequence:
            print(-1)
            solved = True
            break

        # xor a_i with b bitstring
        curr_a = xor_bitstrings(curr_a, b_sequence[j][0], num_bits)
        # and update the first_set to match modified bitstring
        first_set = curr_a.find('1')
        # and track the max b_i index we used to create curr_a
        if b_sequence[j][1] > max_b:
            max_b = b_sequence[j][1]

    # print for ones we haven't finished yet
    if solved == False:
        # if we've come to the end and curr_a isn't all 0s,
        # there's no way to get it as an xor the b's
        if first_set != -1:
            print(-1)
        # otherwise, a has been created from the b's, so print
        # the index of the max b we used (+1, because 0-based indexing)
        else:
            print(max_b+1)