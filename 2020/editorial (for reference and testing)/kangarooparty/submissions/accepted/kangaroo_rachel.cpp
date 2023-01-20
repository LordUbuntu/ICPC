#include <iostream>
#include <vector>
#include <cmath>
using namespace std;

int main()
{
    int n;
    vector<int> houses;
    int cost, best_cost = -1;

    cin >> n;

    // read kangaroo info
    houses = vector<int>(n);
    for (int i = 0; i < n; i++)
        cin >> houses[i];

    // try all possible pairs of host kangaroos
    for (int i = 0; i < n; i++)     //first host
    {
        for (int j = i; j < n; j++)     //second host
        {
            //compute cost for these hosts
            cost = 0;
            for (int k = 0; k < n; k++)
            {
                if (abs(houses[k] - houses[i]) < abs(houses[k] - houses[j]))
                    cost += pow(houses[k] - houses[i], 2);
                else
                    cost += pow(houses[k] - houses[j], 2);
            }

            // if energy cost for this host pair is better than current best,
            // save this as new solution
            if (cost < best_cost || best_cost == -1)
                best_cost = cost;
        }
    }

    // print lowest possible energy cost
    cout << best_cost << endl;
}