def main():
    n = int(input())
    a = [int(input()) for _ in range(n)]
    s = sum(a)
    for x in a:
        if x + x == s:
            print(x)
            return

    print("BAD")

if __name__ == "__main__":
    main()
