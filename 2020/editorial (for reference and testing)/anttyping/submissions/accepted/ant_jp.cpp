#include <iostream>
#include <algorithm>
#include <vector>
using namespace std;
int cnt[9][9];
inline int abs(int x) { return ((x<0)?(-x):x); }
int main()
{
	string S;
	cin>>S;
	for(int i=1;i<S.length();i++) cnt[S[i]-'1'][S[i-1]-'1']++;
	int ans=-1;
	vector<int> perm(9, 0);
	for(int i=0;i<9;i++) perm[i]=i;
	do {
		int sum=perm[S[0]-'1'];
		for(int i=0;i<9;i++)
			for(int j=i+1;j<9;j++)
				sum+=(cnt[i][j]+cnt[j][i])*abs(perm[i]-perm[j]);
		if(ans == -1 || ans>sum) ans=sum;
	} while(next_permutation(perm.begin(), perm.end()));
	ans+=S.length();
	cout << ans << endl;
	return 0;
}
