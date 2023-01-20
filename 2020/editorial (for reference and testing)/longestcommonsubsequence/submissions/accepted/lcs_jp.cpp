#include <string>
#include <iostream>
#include <algorithm>
using namespace std;
int N, K;
bool adj[30][30];
int dp[30];
int main()
{
	for(int i=0;i<26;i++)
	{
		for(int j=0;j<26;j++)
			adj[i][j] = true;
		adj[i][i] = false;
		dp[i] = -1;
	}
	cin >> N >> K;
	for(int k=1;k<=N;k++)
	{
		string a;
		cin >> a;
		for(int i=0;i<K;i++)
		{
			for(int j=i+1;j<K;j++)
			{
				// Can't go from the jth letter to ith
				adj[a[j]-'A'][a[i]-'A'] = false;
			}
		}
	}
	int ans = 0;
	while(true)
	{
		bool updated = false;
		for(int i=0;i<K;i++)
		{
			if(dp[i] != -1) continue;
			bool flag = false;
			int best = 0;
			for(int j=0;j<K;j++)
			{
				if(!adj[i][j]) continue;
				if(dp[j] == -1) { flag=true; break; }
				best = max(best, dp[j]);
			}
			if(!flag)
			{
				dp[i] = best+1;
				ans = max(ans, dp[i]);
				updated = true;
			}
		}
		if(!updated) break;
	}
	cout << ans << endl;
	return 0;
}
