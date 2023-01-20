#include <stdio.h>
#include <stdlib.h>
int v[1000000] ;
int cmp(const void *a, const void *b) {
   return *(int*)a - *(int*)b ;
}
int main() {
   int n ;
   if (scanf("%d", &n) != 1)
      exit(10) ;
   for (int i=0; i<n; i++)
      if (scanf("%d", v+i) != 1)
         exit(10) ;
   qsort(v, n, sizeof(v[0]), cmp) ;
   int r = 0 ;
   for (int i=0; i<n; i++)
      if (i==0 || v[i] != 1+v[i-1])
         r += v[i] ;
   printf("%d\n", r) ;
}
