#!python3

n, m, k = (int(x) for x in input().split())
l = [int(input(), 16) for _ in range(n)]
basis = [] # val, bit, idx
first_bad = None
for lidx, val in enumerate(l):
  cval = val
  for bval, bit, _ in basis:
    if cval & (2 ** bit):
      cval ^= bval
  if cval == 0 and first_bad is None:
    first_bad = lidx+1
  if cval:
    fbit = 0
    while (cval & (2 ** fbit)) == 0:
      fbit += 1
    basis.append([cval, fbit, lidx+1])

for _ in range(m):
  val = int(input(), 16)
  if val == 0:
    if first_bad:
      print(first_bad)
    else:
      print(-1)
    continue
  assert val
  ret = -1
  for cval, obit, idx in basis:
    if val & (2 ** obit):
      val ^= cval
      if val == 0:
        ret = idx
        break
  print(ret)