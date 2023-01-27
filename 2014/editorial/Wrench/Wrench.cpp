#include <iostream>
#include <cstdio>
#include <queue>
#include <map>
#include <cassert>
#include <cstring>
#include <cmath>
#include <algorithm>
#include <vector>
#include <stack>

using namespace std;

char buf[20];

int periodLoc() {
	int len = strlen(buf);
	for(int i = 0; i < len; i++) {
		if(buf[i] == '.') return i;
	}
	return -1;
}

long long obtain() {
	int len = strlen(buf);
	long long ret = 0;
	for(int i = 0; i < len; i++) {
		if(buf[i] == '.') continue;
		ret *= 10;
		ret += buf[i] - '0';
	}
	return ret;
}

int main() {
	int t;
	scanf("%d\n", &t);
	while(t--) {
		memset(buf, 0, sizeof(buf));
		scanf("%s", buf);
		int len = strlen(buf);
		int numDecimal = 0;
		if(periodLoc() >= 0) {
			numDecimal = len - periodLoc() - 1;
		}
		if(numDecimal == 0) {
			if(periodLoc() < 0) {
				buf[len++] = '.';
			}
			buf[len++] = '0';
			numDecimal = 1;
		}
		long long desired = obtain();
		long long scaleNum = 1;
		for(int a = 0; a < numDecimal; a++) {
			scaleNum *= 10;
		}
		int count = 0;
		for(int dem = 1; count == 0 && dem <= 128; dem *= 2) {
			for(int num = 0; num <= 10 * dem; num++) {
				long long actualNum = (num * scaleNum);
				if(actualNum / dem == desired || (actualNum + dem - 1) / dem == desired) {
					count++;
					if(num >= dem) {
						printf("%d", num/dem);
						if(num%dem != 0) {
							printf(" %d/%d\"\n", (num%dem), dem);
						}
						else {
							printf("\"\n");
						}
					}
					else {
						printf("%d/%d\"\n", num, dem);
					}
				}
			}
		}
		assert(count == 1);
	}
}

