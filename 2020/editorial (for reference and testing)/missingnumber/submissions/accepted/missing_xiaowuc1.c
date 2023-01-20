#include <stdio.h>
#include <string.h>

int n;
char buf[10005];
int main() {
  scanf("%d %s", &n, buf);
  int idx = 0;
  for(int ret = 1; ret <= n; ret++) {
    int v = 0;
    while(v < ret && idx < strlen(buf)) {
      v = 10 * v + (buf[idx++] - '0');
    }
    if(v != ret) {
      printf("%d\n", ret);
      return 0;
    }
  }
  return 1;
}