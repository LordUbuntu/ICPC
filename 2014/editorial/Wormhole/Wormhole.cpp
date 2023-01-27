#include <iostream>
#include <vector>
#include <algorithm>
#include <string>
#include <cmath>
#include <map>
#include <iomanip>

using namespace std;

// To output a matrix
ostream & operator<<(ostream & os, vector< vector< long double > > & rhs) {
	for (int i = 0; i<rhs.size(); i++) {
		for (int j = 0; j< rhs[i].size(); j++)
			os << rhs[i][j] << " ";
		os << endl;
	}
	return os;
}

void Floyd(vector< vector< long double > > & D, vector< vector< long double > > & W)
{
	int n = W.size();
	D = W;
	for (int k = 0; k < n; k++) {
		for (int i = 0; i<n; i++) {
			for (int j = 0; j<n; j++) {
				D[i][j] = min(D[i][j], D[i][k] + D[k][j]);
			}
		}
	}
}

class point {
public:
	long double x, y, z;
	point() : x(0.0), y(0.0), z(0.0) {}
	point(long double X, long double Y, long double Z) : x(X), y(Y), z(Z) { }
};

// distance between two points
inline double length(point & p1, point & p2) {
	return sqrt((p2.x - p1.x)*(p2.x - p1.x) + (p2.y - p1.y)*(p2.y - p1.y) + (p2.z - p1.z)*(p2.z - p1.z));
}

map<string, int> pid; // planet name to id map
vector<string> planets;  // planet names
vector<point> p;         // planet positions
vector< vector< long double> > D, W;  // Distances and Weights

int main() {

	int cases, c = 1;
	cin >> cases;
	while (cases--> 0)
	{
		int n, w;
		
		// Read in the planet positions
		cin >> n;		
		planets.clear();	
		planets.resize(n); // planet names	
		p.clear();		
		p.resize(n);       // planet positions		
		pid.clear();	   // planet name to id map 
		for (int i = 0; i<n; i++) {
			cin >> planets[i] >> p[i].x >> p[i].y >> p[i].z;
			pid[planets[i]] = i;
		}

		// Read in the wormholes and set the shortcuts
		W.clear();
		W.resize(n);
		for (int k = 0; k < n; k++) W[k].resize(n, -1.0);
		cin >> w;
		string wstart, wend;
		for (int i = 0; i<w; i++) {
			cin >> wstart >> wend;
			W[pid[wstart]][pid[wend]] = 0.0;
		}

		// Setup the weight matrix between every pair of planets
		for (int i = 0; i<n; i++) {
			for (int j = 0; j<n; j++) {
				if (W[i][j] < 0.0) {
					W[i][j] = length(p[i], p[j]);
				}
			}
		}

		// Recompute a new distance matrix
		D.clear();
		D.resize(n);
		for (int k = 0; k < n; k++) D[k].resize(n, -1.0);

		// Run Floyd's algorithm to generate D from W	
		Floyd(D, W);
		
		//	cout << "W------------" << endl << W << endl;
		//	cout << "D------------" << endl << D << endl;			

		// Answer the queries
		cout << "Case " << c++ << ":" << endl;		
		int q;
		cin >> q;
		for (int i = 0; i<q; i++) {
			cin >> wstart >> wend;
			cout << "The distance from " << wstart << " to " << wend << " is " << fixed << setprecision(0) << round(D[pid[wstart]][pid[wend]]) << " parsecs." << endl;
		}
	}
	return 0;
}
