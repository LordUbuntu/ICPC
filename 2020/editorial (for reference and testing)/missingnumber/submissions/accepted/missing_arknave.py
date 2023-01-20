#!python3
def main():
    n = input()
    s = input()
    n = int(n)
    for x in range(1, n + 1):
        str_x = str(x)
        if not s.startswith(str_x):
            print(x)
            return

        s = s[len(str_x):]

if __name__ == "__main__":
    main()
