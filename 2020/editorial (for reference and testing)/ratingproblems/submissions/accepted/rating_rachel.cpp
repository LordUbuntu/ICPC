#include <iostream>
#include <iomanip>
using namespace std;

int main()
{
    int n, k;
    int curr_rating;
    int rating_sum = 0;

    cin >> n >> k;

    // read and sum current ratings
    for (int i = 0; i < k; i++)
    {
        cin >> curr_rating;
        rating_sum += curr_rating;
    }

    // compute minimum possible rating, by assuming all other judges rate -3
    double min_rating = (rating_sum + (n-k) * -3) / double(n);

    // compute maximum possible rating, by assuming all other judges rate +3
    double max_rating = (rating_sum + (n-k) * 3) / double(n);

    // print rounded results
    cout << fixed << setprecision(8) << min_rating << " " << max_rating << endl;
}