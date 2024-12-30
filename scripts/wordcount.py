import sys

if len(sys.argv) == 0:
    print("No arguments provided")
else:
    text = sys.argv[1]
    words = text.split()
    seen = {}
    for word in words:
        print(len(word))
        seen[len(word)] = seen.get(len(word), 0) + 1
    print("Summary of seen lengths:")
    for length, count in seen.items():
        print(f"{count} word(s) of length {length}")