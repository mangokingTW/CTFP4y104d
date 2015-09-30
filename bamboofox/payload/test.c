#include <stdio.h>
#include <string.h>

int main(){
	int i;
	char *a = (char *)malloc(20);
	memset(a,0,20);
	sprintf(a,"aaaaaaaaaaaaaa");
	free(a);
	a = (char *)malloc(20);
	sprintf(a,"%ld ",12);
	strncat(a,"ccccc",5);
	for( i = 0 ; i < 20 ; i++ )
		putchar(a[i]);
	printf("\n%s\n",a);
	return 0;
}
