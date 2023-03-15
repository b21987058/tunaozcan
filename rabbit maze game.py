board1 =  input('Please enter feeding map as a list:',  )
dir_input = input('Please enter direction of movements as a list:')

board2 = board1[1:]
board2 = board2[:-1]
maze = [] #this is our board before the instructions

for char in board2:
    if char == '[':
        new = []
    elif char == ']':
        maze.append(new)
    elif char == ',' or char == "'" or char == " ":
        continue
    else:
        new.append(char)

print('Your bourd is:')

for k in maze:
    print(' '.join(k))


direction_list = []
for i in dir_input :
    if i == 'U' or i == 'D' or i == 'R' or i == 'L' :
        direction_list.append(i)
    else:
        continue


a = 0
n1 = -1
n2 = -1
for t in maze:
    n1 += +1
    n2 = -1
    for u in t:
        n2 += +1
        if u == '*':
            a = 1
            break
    if a == 1 :
        break



score = 0
def game(maze,n1,n2,direction_list) :
    global score
    row  = 0
    column = 0
    for y in maze:
        row += 1
        for j in y:
            column += 1
    for z in range(len(direction_list)):
        if direction_list[z] == 'U':
            maze[n1][n2] = 'X'
            n1= n1-1
            if n1 < 0:
                n1 = n1 + 1
                continue
            if maze[n1][n2] == 'W':
                n1 = n1+1
                maze[n1][n2] = '*'
            elif maze[n1][n2] == 'X' :
                maze[n1][n2] = '*'
            elif maze[n1][n2] == 'C':
                score = score + 10
                maze[n1][n2] = '*'
            elif maze[n1][n2] == 'A':
                score = score + 5
                maze[n1][n2] = '*'
            elif maze[n1][n2] == 'M':
                score = score - 5
                maze[n1][n2] = '*'
            elif maze[n1][n2] == 'P':
                maze[n1][n2] = '*'
                break

        elif direction_list[z] =='D':
            maze[n1][n2] = 'X'
            n1= n1+1
            if n1 > row:
                n1 = n1 - 1
                continue
            if maze[n1][n2] == 'W':
                n1 = n1-1
                maze[n1][n2] = '*'
            elif maze[n1][n2] == 'X' :
                maze[n1][n2] = '*'
            elif maze[n1][n2] == 'C':
                score = score + 10
                maze[n1][n2] = '*'
            elif maze[n1][n2] == 'A':
                score = score + 5
                maze[n1][n2] = '*'
            elif maze[n1][n2] == 'M':
                score = score - 5
                maze[n1][n2] = '*'
            elif maze[n1][n2] == 'P':
                maze[n1][n2] = '*'
                break

        elif direction_list[z] =='R':
            maze[n1][n2] = 'X'
            n2= n2+1
            if n2 > column:
                n2 =n2 -1
                continue
            if maze[n1][n2] == 'W':
                n2 = n2-1
                maze[n1][n2] = '*'
            elif maze[n1][n2] == 'X' :
                maze[n1][n2] = '*'
            elif maze[n1][n2] == 'C':
                score = score + 10
                maze[n1][n2] = '*'
            elif maze[n1][n2] == 'A':
                score = score + 5
                maze[n1][n2] = '*'
            elif maze[n1][n2] == 'M':
                score = score - 5
                maze[n1][n2] = '*'
            elif maze[n1][n2] == 'P':
                maze[n1][n2] = '*'
                break
        elif direction_list[z] =='L':
            maze[n1][n2] = 'X'
            n2= n2-1
            if n2 > column:
                n2 =n2 +1
                continue
            if maze[n1][n2] == 'W':
                n2 = n2+1
                maze[n1][n2] = '*'
            elif maze[n1][n2] == 'X' :
                maze[n1][n2] = '*'
            elif maze[n1][n2] == 'C':
                score = score + 10
                maze[n1][n2] = '*'
            elif maze[n1][n2] == 'A':
                score = score + 5
                maze[n1][n2] = '*'
            elif maze[n1][n2] == 'M':
                score = score - 5
                maze[n1][n2] = '*'
            elif maze[n1][n2] == 'P':
                maze[n1][n2] = '*'
                break


    return maze
finale_maze = game(maze,n1,n2,direction_list)
print('Your output is: ')
for y in finale_maze:
    print(' '.join(y))
print('Your score:' , score)





















