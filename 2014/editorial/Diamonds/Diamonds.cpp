
// Diamonds Solution
// Author: Kent Jones, Whitworth University
// Description: Same dp algorithm as the trap clearing algorithm.
// 
#include <iostream>
#include <algorithm>
#include <vector>

using namespace std;

// Compare two diamonds
bool operator<(const pair<double, double> & a, const pair<double, double> & b) {
	if ( (a.first < b.first) && (a.second > b.second) ) return true;
	return false;
}

int main()
{
	int n, cases, lmax;
	double carats, clarity;
	cin >> cases;
	
	while (cases --> 0) {
		vector< pair<double, double> > dp;  // diamond pile
		vector< int > dpt;  // dp table

		cin >> n;
		lmax = 0;
		dpt.resize(n,1);
		for (int i = 0; i<n; i++) {
			cin >> carats >> clarity;
			dp.push_back(make_pair(carats, clarity));
		}
		
		// For each diamond, find longest chain ending on that diamond
		for ( int i=0; i<n; i++ ) {
			for ( int j=0; j<i; j++ ) {
				if ( dp[j] < dp[i] ) {
					dpt[i] = max( dpt[i], dpt[j]+1 );
				}
			}
			lmax = max( lmax, dpt[i] );
		}
		cout << lmax << endl;
	}

	return 0;
}


