// Knights
// solution: Dan Adkins
// Fast matrix exponentiation

#include <assert.h>
#include <stdio.h>
#include <string.h>

// The number of rows is very small, at most 4.  The key observation
// is that to avoid placing a knight on a square that is attacked, we
// only need to know the location of the knights in the previous two
// columns. The number of possible states is small: (2^4)^2 = 16.
// Thus, we can think of the problem of counting the number of knight
// layouts as paths in a graph of these states.  We must first
// determine the transition matrix between states, as we slide the
// window over one column. Then, to solve for the large number of
// rows, we can use fast exponentiation on the matrix.

// Maximum number of states.
#define MAXN 256
#define MOD 1000000009LL

typedef long long int64;

int nrows, ncols;
int nstates;
int64 adj[MAXN][MAXN];
int64 m[MAXN][MAXN], mm[MAXN][MAXN];

// state layout (for nrows=4)
// bits 0..3  4..7
// col  n-2  n-1

// col is 0 or 1, row is in [0, nrows)
int on(int state, int col, int row) {
    return state & (1 << (col*nrows + row));
}

// Is the knight layout of the next column feasible, given the state
// of the previous two rows?
int isfeasible(int state, int next) {
    int r;

    for (r = 0; r < nrows; r++) {
	if (!on(next, 0, r)) {
	    continue;
	}
	// Check four possible knight moves.
	if (r-2 >= 0 && on(state, 1, r-2)) { return 0; }
	if (r-1 >= 0 && on(state, 0, r-1)) { return 0; }
	if (r+1 < nrows && on(state, 0, r+1)) { return 0; }
	if (r+2 < nrows && on(state, 1, r+2)) { return 0; }
    }
    return 1;
}

// Make the adjacency matrix.
void mkadj() {
    int i, j, k;

    memset(adj, 0, sizeof(adj));
    nstates = 1 << (2*nrows);
    for (i = 0; i < nstates; i++) {
	// For each state (configuration of previous two rows), try
	// all possible configurations of knights on the current now.
	for (j = 0; j < (1 << nrows); j++) {
	    if (isfeasible(i, j)) {
		// The next state is easily constructed by shifting.
		k = (i >> nrows) | (j << nrows);
		adj[i][k] = 1;
	    }
	}
    }
}

// matrix multiplication: a*b -> c
void mul(int64 a[][MAXN], int64 b[][MAXN], int64 c[][MAXN]) {
    int i, j, k;

    for (i = 0; i < nstates; i++) {
	for (j = 0; j < nstates; j++) {
	    c[i][j] = 0;
	    for (k = 0; k < nstates; k++) {
		c[i][j] += a[i][k] * b[k][j];
		c[i][j] %= MOD;
	    }
	}
    }   
}

// Fast exponentiation (of adj) by squaring.
void fastexp(int n) {
    int i;

    // Initialize m to the identity.
    memset(m, 0, sizeof(m));
    for (i = 0; i < nstates; i++) {
	m[i][i] = 1;
    }

    while (n > 0) {
	if (n % 2 == 1) {
	    mul(m, adj, mm);
	    memmove(m, mm, sizeof(mm));
	    n--;
	}
	mul(adj, adj, mm);
	memmove(adj, mm, sizeof(mm));
	n /= 2;
    }
}

// The initial state is 0, but all final states are allowed.
// Sum accordingly.
int64 nlayouts() {
    int i;
    int64 res;

    res = 0;
    for (i = 0; i < nstates; i++) {
	res += m[0][i];
	res %= MOD;
    }
    return res;
}

int main() {
    int i, ncases;

    assert(scanf(" %d", &ncases) == 1);
    for (i = 0; i < ncases; i++) {
	assert(scanf(" %d %d", &nrows, &ncols) == 2);
	mkadj();
	fastexp(ncols);
	printf("%lld\n", nlayouts());
    }
    return 0;
}
