import sys

board = [['R1', 'N1', 'B1', 'QU', 'KI', 'B2', 'N2', 'R2'], ['P1', 'P2', 'P3', 'P4', 'P5', 'P6', 'P7', 'P8'],
         ['  ', '  ', '  ', '  ', '  ', '  ', '  ', '  '], ['  ', '  ', '  ', '  ', '  ', '  ', '  ', '  '],
         ['  ', '  ', '  ', '  ', '  ', '  ', '  ', '  '], ['  ', '  ', '  ', '  ', '  ', '  ', '  ', '  '],
         ['p1', 'p2', 'p3', 'p4', 'p5', 'p6', 'p7', 'p8'], ['r1', 'n1', 'b1', 'qu', 'ki', 'b2', 'n2', 'r2']]
coordinates = [['a8', 'b8', 'c8', 'd8', 'e8', 'f8', 'g8', 'h1'], ['a7', 'b7', 'c7', 'd7', 'e7', 'f7', 'g7', 'h7'],
               ['a6', 'b6', 'c6', 'd6', 'e6', 'f6', 'g6', 'h6'], ['a5', 'b5', 'c5', 'd5', 'e5', 'f5', 'g5', 'h5'],
               ['a4', 'b4', 'c4', 'd4', 'e4', 'f4', 'g4', 'h4'], ['a3', 'b3', 'c3', 'd3', 'e3', 'f3', 'g3', 'h3'],
               ['a2', 'b2', 'c2', 'd2', 'e2', 'f2', 'g2', 'h2'], ['a1', 'b1', 'c1', 'd1', 'e1', 'f1', 'g1', 'h1']]
board2 = [['R1', 'N1', 'B1', 'QU', 'KI', 'B2', 'N2', 'R2'], ['P1', 'P2', 'P3', 'P4', 'P5', 'P6', 'P7', 'P8'],
          ['  ', '  ', '  ', '  ', '  ', '  ', '  ', '  '], ['  ', '  ', '  ', '  ', '  ', '  ', '  ', '  '],
          ['  ', '  ', '  ', '  ', '  ', '  ', '  ', '  '], ['  ', '  ', '  ', '  ', '  ', '  ', '  ', '  '],
          ['p1', 'p2', 'p3', 'p4', 'p5', 'p6', 'p7', 'p8'], ['r1', 'n1', 'b1', 'qu', 'ki', 'b2', 'n2', 'r2']]


def initialize():
    global board
    board = [['R1', 'N1', 'B1', 'QU', 'KI', 'B2', 'N2', 'R2'], ['P1', 'P2', 'P3', 'P4', 'P5', 'P6', 'P7', 'P8'],
             ['  ', '  ', '  ', '  ', '  ', '  ', '  ', '  '], ['  ', '  ', '  ', '  ', '  ', '  ', '  ', '  '],
             ['  ', '  ', '  ', '  ', '  ', '  ', '  ', '  '], ['  ', '  ', '  ', '  ', '  ', '  ', '  ', '  '],
             ['p1', 'p2', 'p3', 'p4', 'p5', 'p6', 'p7', 'p8'], ['r1', 'n1', 'b1', 'qu', 'ki', 'b2', 'n2', 'r2']]
    print(23 * "-")
    for t in board:
        print(' '.join(t))
    print(23 * "-")


f = open(sys.argv[1], "r")
commands = [[line.split()] for line in f.readlines()]
f.close()


def showmoves(piece):
    possible_moves = []
    location = []
    location2 = []
    a = -1
    b = -1
    for y in range(8):
        for z in range(8):
            if board[y][z] == piece:
                location = [y, z]
                a = z
                b = y
    if not a == -1:  # hangi sütunda oldugu
        location2.append(chr(97 + a))
    if not b == -1:  # hangi satırda oldugu
        location2.append(str(8 - b))
    # print('location of',piece,'is:', location2[0]+location2[1])

    if piece[0] == 'p':
        if board[b - 1][a].islower() == False:
            possible_moves.append(location2[0] + str((int(location2[1])) + 1))

    elif piece[0] == 'P':
        if board[b + 1][a].isupper() == False:
            possible_moves.append(location2[0] + str((int(location2[1])) - 1))

    elif piece == 'ki':
        if (0 <= b - 1 < 8) and (0 <= a < 8) and board[b - 1][a].islower() == False:
            possible_moves.append(location2[0] + str((int(location2[1])) + 1))
        if (0 <= b + 1 < 8) and (0 <= a < 8) and board[b + 1][a].islower() == False:
            possible_moves.append(location2[0] + str((int(location2[1])) - 1))
        if (0 <= b < 8) and (0 <= a + 1 < 8) and board[b][a + 1].islower() == False:
            possible_moves.append(chr(ord(location2[0]) + 1) + str((int(location2[1]))))
        if (0 <= b < 8) and (0 <= a - 1 < 8) and board[b][a - 1].islower() == False:
            possible_moves.append(chr(ord(location2[0]) - 1) + str((int(location2[1]))))
        if (0 <= b - 1 < 8) and (0 <= a - 1 < 8) and board[b - 1][a - 1].islower() == False:
            possible_moves.append(chr(ord(location2[0]) - 1) + str((int(location2[1])) + 1))
        if (0 <= b - 1 < 8) and (0 <= a + 1 < 8) and board[b - 1][a + 1].islower() == False:
            possible_moves.append(chr(ord(location2[0]) + 1) + str((int(location2[1])) + 1))
        if (0 <= b + 1 < 8) and (0 <= a - 1 < 8) and board[b + 1][a - 1].islower() == False:
            possible_moves.append(chr(ord(location2[0]) - 1) + str((int(location2[1])) - 1))
        if (0 <= b + 1 < 8) and (0 <= a + 1 < 8) and board[b + 1][a + 1].islower() == False:
            possible_moves.append(chr(ord(location2[0]) + 1) + str((int(location2[1])) - 1))

    elif piece == 'KI':
        if (0 <= b - 1 < 8) and (0 <= a < 8) and board[b - 1][a].isupper() == False:
            possible_moves.append(location2[0] + str((int(location2[1])) + 1))
        if (0 <= b + 1 < 8) and (0 <= a < 8) and board[b + 1][a].isupper() == False:
            possible_moves.append(location2[0] + str((int(location2[1])) - 1))
        if (0 <= b < 8) and (0 <= a + 1 < 8) and board[b][a + 1].isupper() == False:
            possible_moves.append(chr(ord(location2[0]) + 1) + str((int(location2[1]))))
        if (0 <= b < 8) and (0 <= a - 1 < 8) and board[b][a - 1].isupper() == False:
            possible_moves.append(chr(ord(location2[0]) - 1) + str((int(location2[1]))))
        if (0 <= b - 1 < 8) and (0 <= a - 1 < 8) and board[b - 1][a - 1].isupper() == False:
            possible_moves.append(chr(ord(location2[0]) - 1) + str((int(location2[1])) + 1))
        if (0 <= b - 1 < 8) and (0 <= a + 1 < 8) and board[b - 1][a + 1].isupper() == False:
            possible_moves.append(chr(ord(location2[0]) + 1) + str((int(location2[1])) + 1))
        if (0 <= b + 1 < 8) and (0 <= a - 1 < 8) and board[b + 1][a - 1].isupper() == False:
            possible_moves.append(chr(ord(location2[0]) - 1) + str((int(location2[1])) - 1))
        if (0 <= b + 1 < 8) and (0 <= a + 1 < 8) and board[b + 1][a + 1].isupper() == False:
            possible_moves.append(chr(ord(location2[0]) + 1) + str((int(location2[1])) - 1))

    elif piece[0] == 'n':
        if (0 <= b - 2 < 8) and (0 <= a + 1 < 8) and board[b - 2][a + 1].islower() == False:
            possible_moves.append(chr(ord(location2[0]) + 1) + str((int(location2[1])) + 2))
        if (0 <= b - 2 < 8) and (0 <= a - 1 < 8) and board[b - 2][a - 1].islower() == False:
            possible_moves.append(chr(ord(location2[0]) - 1) + str((int(location2[1])) + 2))
        if (0 <= b - 1 < 8) and (0 <= a - 2 < 8) and board[b - 1][a - 2].islower() == False:
            possible_moves.append(chr(ord(location2[0]) - 2) + str((int(location2[1])) + 1))
        if (0 <= b - 1 < 8) and (0 <= a + 2 < 8) and board[b - 1][a + 2].islower() == False:
            possible_moves.append(chr(ord(location2[0]) + 2) + str((int(location2[1])) + 1))

        if (0 <= b + 2 < 8) and (0 <= a - 1 < 8) and board[b + 2][a - 1].islower() == False:
            possible_moves.append(chr(ord(location2[0]) - 1) + str((int(location2[1])) - 2))
        if (0 <= b + 2 < 8) and (0 <= a + 1 < 8) and board[b + 2][a + 1].islower() == False:
            possible_moves.append(chr(ord(location2[0]) + 1) + str((int(location2[1])) - 2))
        if (0 <= b + 1 < 8) and (0 <= a - 2 < 8) and board[b + 1][a - 2].islower() == False:
            possible_moves.append(chr(ord(location2[0]) - 2) + str((int(location2[1])) - 1))
        if (0 <= b + 1 < 8) and (0 <= a + 2 < 8) and board[b + 1][a + 2].islower() == False:
            possible_moves.append(chr(ord(location2[0]) + 2) + str((int(location2[1])) - 1))

        if (0 <= b + 1 < 8) and (0 <= a + 1 < 8) and board[b + 1][a + 1] == '  ':
            possible_moves.append(chr(ord(location2[0]) + 1) + str((int(location2[1])) - 1))
        if (0 <= b + 1 < 8) and (0 <= a - 1 < 8) and board[b + 1][a - 1] == '  ':
            possible_moves.append(chr(ord(location2[0]) - 1) + str((int(location2[1])) - 1))
        if (0 <= b - 1 < 8) and (0 <= a + 1 < 8) and board[b - 1][a + 1] == '  ':
            possible_moves.append(chr(ord(location2[0]) + 1) + str((int(location2[1])) + 1))
        if (0 <= b - 1 < 8) and (0 <= a - 1 < 8) and board[b - 1][a - 1] == '  ':
            possible_moves.append(chr(ord(location2[0]) - 1) + str((int(location2[1])) + 1))

    elif piece[0] == 'N':
        if (0 <= b - 2 < 8) and (0 <= a + 1 < 8) and board[b - 2][a + 1].isupper() == False:
            possible_moves.append(chr(ord(location2[0]) + 1) + str((int(location2[1])) + 2))
        if (0 <= b - 2 < 8) and (0 <= a - 1 < 8) and board[b - 2][a - 1].isupper() == False:
            possible_moves.append(chr(ord(location2[0]) - 1) + str((int(location2[1])) + 2))
        if (0 <= b - 1 < 8) and (0 <= a - 2 < 8) and board[b - 1][a - 2].isupper() == False:
            possible_moves.append(chr(ord(location2[0]) - 2) + str((int(location2[1])) + 1))
        if (0 <= b - 1 < 8) and (0 <= a + 2 < 8) and board[b - 1][a + 2].isupper() == False:
            possible_moves.append(chr(ord(location2[0]) + 2) + str((int(location2[1])) + 1))

        if (0 <= b + 2 < 8) and (0 <= a - 1 < 8) and board[b + 2][a - 1].isupper() == False:
            possible_moves.append(chr(ord(location2[0]) - 1) + str((int(location2[1])) - 2))
        if (0 <= b + 2 < 8) and (0 <= a + 1 < 8) and board[b + 2][a + 1].isupper() == False:
            possible_moves.append(chr(ord(location2[0]) + 1) + str((int(location2[1])) - 2))
        if (0 <= b + 1 < 8) and (0 <= a - 2 < 8) and board[b + 1][a - 2].isupper() == False:
            possible_moves.append(chr(ord(location2[0]) - 2) + str((int(location2[1])) - 1))
        if (0 <= b + 1 < 8) and (0 <= a + 2 < 8) and board[b + 1][a + 2].isupper() == False:
            possible_moves.append(chr(ord(location2[0]) + 2) + str((int(location2[1])) - 1))

        if (0 <= b + 1 < 8) and (0 <= a + 1 < 8) and board[b + 1][a + 1] == '  ':
            possible_moves.append(chr(ord(location2[0]) + 1) + str((int(location2[1])) - 1))
        if (0 <= b + 1 < 8) and (0 <= a - 1 < 8) and board[b + 1][a - 1] == '  ':
            possible_moves.append(chr(ord(location2[0]) - 1) + str((int(location2[1])) - 1))
        if (0 <= b - 1 < 8) and (0 <= a + 1 < 8) and board[b - 1][a + 1] == '  ':
            possible_moves.append(chr(ord(location2[0]) + 1) + str((int(location2[1])) + 1))
        if (0 <= b - 1 < 8) and (0 <= a - 1 < 8) and board[b - 1][a - 1] == '  ':
            possible_moves.append(chr(ord(location2[0]) - 1) + str((int(location2[1])) + 1))

    elif piece[0] == 'r':
        for c in range(1, 8):
            if (0 <= b < 8) and (0 <= a + c < 8) and (board[b][a + c].islower() == False):
                possible_moves.append(chr(ord(location2[0]) + c) + str((int(location2[1]))))
            if (0 <= b < 8) and (0 <= a + c < 8) and (board[b][a + c].islower() == True):
                break
            if (0 <= b < 8) and (0 <= a + c < 8) and board[b][a + c].isupper() == True:
                break
        for c in range(1, 8):
            if (0 <= b < 8) and (0 <= a - c < 8) and (board[b][a - c].islower() == False):
                possible_moves.append(chr(ord(location2[0]) - c) + str((int(location2[1]))))
            if (0 <= b < 8) and (0 <= a - c < 8) and (board[b][a - c].islower() == True):
                break
            if (0 <= b < 8) and (0 <= a - c < 8) and board[b][a - c].isupper() == True:
                break
        for c in range(1, 8):
            if (0 <= b + c < 8) and (0 <= a < 8) and board[b + c][a].islower() == False:
                possible_moves.append(chr(ord(location2[0])) + str((int(location2[1])) - c))
            if (0 <= b + c < 8) and (0 <= a < 8) and board[b + c][a].islower() == True:
                break
            if (0 <= b + c < 8) and (0 <= a < 8) and board[b + c][a].isupper() == True:
                break
        for c in range(1, 8):
            if (0 <= b - c < 8) and (0 <= a < 8) and board[b - c][a].islower() == False:
                possible_moves.append(chr(ord(location2[0])) + str((int(location2[1])) + c))
            if (0 <= b - c < 8) and (0 <= a < 8) and board[b - c][a].islower() == True:
                break
            if (0 <= b - c < 8) and (0 <= a < 8) and board[b - c][a].isupper() == True:
                break


    elif piece[0] == 'R':
        for c in range(1, 8):
            if (0 <= b < 8) and (0 <= a + c < 8) and board[b][a + c].isupper() == False:
                possible_moves.append(chr(ord(location2[0]) + c) + str((int(location2[1]))))
            if (0 <= b < 8) and (0 <= a + c < 8) and board[b][a + c].isupper() == True:
                break
            if (0 <= b < 8) and (0 <= a + c < 8) and board[b][a + c].islower() == True:
                break
        for c in range(1, 8):
            if (0 <= b < 8) and (0 <= a - c < 8) and board[b][a - c].isupper() == False:
                possible_moves.append(chr(ord(location2[0]) - c) + str((int(location2[1]))))
            if (0 <= b < 8) and (0 <= a - c < 8) and board[b][a - c].isupper() == True:
                break
            if (0 <= b < 8) and (0 <= a - c < 8) and board[b][a - c].islower() == True:
                break
        for c in range(1, 8):
            if (0 <= b + c < 8) and (0 <= a < 8) and board[b + c][a].isupper() == False:
                possible_moves.append(chr(ord(location2[0])) + str((int(location2[1])) - c))
            if (0 <= b + c < 8) and (0 <= a < 8) and board[b + c][a].isupper() == True:
                break
            if (0 <= b + c < 8) and (0 <= a < 8) and board[b + c][a].islower() == True:
                break
        for c in range(1, 8):
            if (0 <= b - c < 8) and (0 <= a < 8) and board[b - c][a].isupper() == False:
                possible_moves.append(chr(ord(location2[0])) + str((int(location2[1])) + c))
            if (0 <= b - c < 8) and (0 <= a < 8) and board[b - c][a].isupper() == True:
                break
            if (0 <= b - c < 8) and (0 <= a < 8) and board[b - c][a].islower() == True:
                break

    elif piece[0] == 'B':
        for c in range(1, 8):
            if (0 <= b + c < 8) and (0 <= a + c < 8) and board[b + c][a + c].isupper() == False:
                possible_moves.append(chr(ord(location2[0]) + c) + str((int(location2[1])) - c))
            if (0 <= b + c < 8) and (0 <= a + c < 8) and board[b + c][a + c].isupper() == True:
                break
            if (0 <= b + c < 8) and (0 <= a + c < 8) and board[b + c][a + c].islower() == True:
                break

        for c in range(1, 8):
            if (0 <= b + c < 8) and (0 <= a - c < 8) and board[b + c][a - c].isupper() == False:
                possible_moves.append(chr(ord(location2[0]) - c) + str((int(location2[1])) - c))
            if (0 <= b + c < 8) and (0 <= a - c < 8) and board[b + c][a - c].isupper() == True:
                break
            if (0 <= b + c < 8) and (0 <= a - c < 8) and board[b + c][a - c].islower() == True:
                break

    elif piece[0] == 'b':

        for c in range(1, 8):
            if (0 <= b - c < 8) and (0 <= a + c < 8) and board[b - c][a + c].islower() == False:
                possible_moves.append(chr(ord(location2[0]) + c) + str((int(location2[1])) + c))
            if (0 <= b - c < 8) and (0 <= a + c < 8) and board[b - c][a + c].islower() == True:
                break
            if (0 <= b - c < 8) and (0 <= a + c < 8) and board[b - c][a + c].isupper() == True:
                break

        for c in range(1, 8):
            if (0 <= b - c < 8) and (0 <= a - c < 8) and board[b - c][a - c].islower() == False:
                possible_moves.append(chr(ord(location2[0]) - c) + str((int(location2[1])) + c))
            if (0 <= b - c < 8) and (0 <= a - c < 8) and board[b - c][a - c].islower() == True:
                break
            if (0 <= b - c < 8) and (0 <= a - c < 8) and board[b - c][a - c].isupper() == True:
                break

    elif piece == 'qu':
        for c in range(1, 8):
            if (0 <= b + c < 8) and (0 <= a < 8) and board[b + c][a].islower() == False:
                possible_moves.append(chr(ord(location2[0])) + str((int(location2[1])) - c))
            if (0 <= b + c < 8) and (0 <= a < 8) and board[b + c][a].islower() == True:
                break
            if (0 <= b + c < 8) and (0 <= a < 8) and board[b + c][a].isupper() == True:
                break
        for c in range(1, 8):
            if (0 <= b < 8) and (0 <= a + c < 8) and (board[b][a + c].islower() == False):
                possible_moves.append(chr(ord(location2[0]) + c) + str((int(location2[1]))))
            if (0 <= b < 8) and (0 <= a + c < 8) and (board[b][a + c].islower() == True):
                break
            if (0 <= b < 8) and (0 <= a + c < 8) and board[b][a + c].isupper() == True:
                break
        for c in range(1, 8):
            if (0 <= b + c < 8) and (0 <= a + c < 8) and board[b + c][a + c].islower() == False:
                possible_moves.append(chr(ord(location2[0]) + c) + str((int(location2[1])) - c))
            if (0 <= b + c < 8) and (0 <= a + c < 8) and board[b + c][a + c].isupper() == True:
                break
            if (0 <= b + c < 8) and (0 <= a + c < 8) and board[b + c][a + c].islower() == True:
                break
        for c in range(1, 8):
            if (0 <= b + c < 8) and (0 <= a - c < 8) and board[b + c][a - c].islower() == False:
                possible_moves.append(chr(ord(location2[0]) - c) + str((int(location2[1])) - c))
            if (0 <= b + c < 8) and (0 <= a - c < 8) and board[b + c][a - c].isupper() == True:
                break
            if (0 <= b + c < 8) and (0 <= a - c < 8) and board[b + c][a - c].islower() == True:
                break
        for c in range(1, 8):
            if (0 <= b - c < 8) and (0 <= a + c < 8) and board[b - c][a + c].islower() == False:
                possible_moves.append(chr(ord(location2[0]) + c) + str((int(location2[1])) + c))
            if (0 <= b - c < 8) and (0 <= a + c < 8) and board[b - c][a + c].isupper() == True:
                break
            if (0 <= b - c < 8) and (0 <= a + c < 8) and board[b - c][a + c].islower() == True:
                break
        for c in range(1, 8):
            if (0 <= b - c < 8) and (0 <= a - c < 8) and board[b - c][a - c].islower() == False:
                possible_moves.append(chr(ord(location2[0]) - c) + str((int(location2[1])) + c))
            if (0 <= b - c < 8) and (0 <= a - c < 8) and board[b - c][a - c].isupper() == True:
                break
            if (0 <= b - c < 8) and (0 <= a - c < 8) and board[b - c][a - c].islower() == True:
                break
        for c in range(1, 8):
            if (0 <= b - c < 8) and (0 <= a < 8) and board[b - c][a].islower() == False:
                possible_moves.append(chr(ord(location2[0])) + str((int(location2[1])) + c))
            if (0 <= b - c < 8) and (0 <= a < 8) and board[b - c][a].islower() == True:
                break
            if (0 <= b - c < 8) and (0 <= a < 8) and board[b - c][a].isupper() == True:
                break
        for c in range(1, 8):
            if (0 <= b < 8) and (0 <= a - c < 8) and (board[b][a - c].islower() == False):
                possible_moves.append(chr(ord(location2[0]) - c) + str((int(location2[1]))))
            if (0 <= b < 8) and (0 <= a - c < 8) and (board[b][a - c].islower() == True):
                break
            if (0 <= b < 8) and (0 <= a - c < 8) and board[b][a - c].isupper() == True:
                break

    elif piece == 'QU':
        for c in range(1, 8):
            if (0 <= b + c < 8) and (0 <= a < 8) and board[b + c][a].isupper() == False:
                possible_moves.append(chr(ord(location2[0])) + str((int(location2[1])) - c))
            if (0 <= b + c < 8) and (0 <= a < 8) and board[b + c][a].isupper() == True:
                break
            if (0 <= b + c < 8) and (0 <= a < 8) and board[b + c][a].islower() == True:
                break
        for c in range(1, 8):
            if (0 <= b < 8) and (0 <= a + c < 8) and (board[b][a + c].isupper() == False):
                possible_moves.append(chr(ord(location2[0]) + c) + str((int(location2[1]))))
            if (0 <= b < 8) and (0 <= a + c < 8) and (board[b][a + c].isupper() == True):
                break
            if (0 <= b < 8) and (0 <= a + c < 8) and board[b][a + c].islower() == True:
                break
        for c in range(1, 8):
            if (0 <= b + c < 8) and (0 <= a + c < 8) and board[b + c][a + c].isupper() == False:
                possible_moves.append(chr(ord(location2[0]) + c) + str((int(location2[1])) - c))
            if (0 <= b + c < 8) and (0 <= a + c < 8) and board[b + c][a + c].islower() == True:
                break
            if (0 <= b + c < 8) and (0 <= a + c < 8) and board[b + c][a + c].isupper() == True:
                break
        for c in range(1, 8):
            if (0 <= b + c < 8) and (0 <= a - c < 8) and board[b + c][a - c].isupper() == False:
                possible_moves.append(chr(ord(location2[0]) - c) + str((int(location2[1])) - c))
            if (0 <= b + c < 8) and (0 <= a - c < 8) and board[b + c][a - c].islower() == True:
                break
            if (0 <= b + c < 8) and (0 <= a - c < 8) and board[b + c][a - c].isupper() == True:
                break
        for c in range(1, 8):
            if (0 <= b - c < 8) and (0 <= a + c < 8) and board[b - c][a + c].isupper() == False:
                possible_moves.append(chr(ord(location2[0]) + c) + str((int(location2[1])) + c))
            if (0 <= b - c < 8) and (0 <= a + c < 8) and board[b - c][a + c].islower() == True:
                break
            if (0 <= b - c < 8) and (0 <= a + c < 8) and board[b - c][a + c].isupper() == True:
                break
        for c in range(1, 8):
            if (0 <= b - c < 8) and (0 <= a - c < 8) and board[b - c][a - c].isupper() == False:
                possible_moves.append(chr(ord(location2[0]) - c) + str((int(location2[1])) + c))
            if (0 <= b - c < 8) and (0 <= a - c < 8) and board[b - c][a - c].islower() == True:
                break
            if (0 <= b - c < 8) and (0 <= a - c < 8) and board[b - c][a - c].isupper() == True:
                break
        for c in range(1, 8):
            if (0 <= b - c < 8) and (0 <= a < 8) and board[b - c][a].isupper() == False:
                possible_moves.append(chr(ord(location2[0])) + str((int(location2[1])) + c))
            if (0 <= b - c < 8) and (0 <= a < 8) and board[b - c][a].islower() == True:
                break
            if (0 <= b - c < 8) and (0 <= a < 8) and board[b - c][a].isupper() == True:
                break
        for c in range(1, 8):
            if (0 <= b < 8) and (0 <= a - c < 8) and (board[b][a - c].isupper() == False):
                possible_moves.append(chr(ord(location2[0]) - c) + str((int(location2[1]))))
            if (0 <= b < 8) and (0 <= a - c < 8) and (board[b][a - c].isupper() == True):
                break
            if (0 <= b < 8) and (0 <= a - c < 8) and board[b][a - c].islower() == True:
                break
    if not possible_moves:
        print("FAILED")
    else:
        possible_moves.sort()
        for moves in possible_moves:
            if board[8 - int(moves[1])][ord(moves[0]) - 97] == "ki" or board[8 - int(moves[1])][
                ord(moves[0]) - 97] == "KI":
                possible_moves.remove(moves)
        for moves in possible_moves:
            print(moves, end=' ')
        print()


def move(piece, position):
    location2 = []
    possible_moves = []
    a = -1
    b = -1
    for y in range(8):
        for z in range(8):
            if board[y][z] == piece:
                location = [y, z]
                a = z
                b = y
    if not a == -1:  # hangi sütunda oldugu
        location2.append(chr(97 + a))

    if not b == -1:  # hangi satırda oldugu
        location2.append(str(8 - b))
    # print('location of',piece,'is:', location2[0]+location2[1]) taşın pozisyonu

    if piece[0] == 'p':
        if board[b - 1][a].islower() == False:
            possible_moves.append(location2[0] + str((int(location2[1])) + 1))

    elif piece[0] == 'P':
        if board[b + 1][a].isupper() == False:
            possible_moves.append(location2[0] + str((int(location2[1])) - 1))

    elif piece == 'ki':
        if (0 <= b - 1 < 8) and (0 <= a < 8) and board[b - 1][a].islower() == False:
            possible_moves.append(location2[0] + str((int(location2[1])) + 1))
        if (0 <= b + 1 < 8) and (0 <= a < 8) and board[b + 1][a].islower() == False:
            possible_moves.append(location2[0] + str((int(location2[1])) - 1))
        if (0 <= b < 8) and (0 <= a + 1 < 8) and board[b][a + 1].islower() == False:
            possible_moves.append(chr(ord(location2[0]) + 1) + str((int(location2[1]))))
        if (0 <= b < 8) and (0 <= a - 1 < 8) and board[b][a - 1].islower() == False:
            possible_moves.append(chr(ord(location2[0]) - 1) + str((int(location2[1]))))
        if (0 <= b - 1 < 8) and (0 <= a - 1 < 8) and board[b - 1][a - 1].islower() == False:
            possible_moves.append(chr(ord(location2[0]) - 1) + str((int(location2[1])) + 1))
        if (0 <= b - 1 < 8) and (0 <= a + 1 < 8) and board[b - 1][a + 1].islower() == False:
            possible_moves.append(chr(ord(location2[0]) + 1) + str((int(location2[1])) + 1))
        if (0 <= b + 1 < 8) and (0 <= a - 1 < 8) and board[b + 1][a - 1].islower() == False:
            possible_moves.append(chr(ord(location2[0]) - 1) + str((int(location2[1])) - 1))
        if (0 <= b + 1 < 8) and (0 <= a + 1 < 8) and board[b + 1][a + 1].islower() == False:
            possible_moves.append(chr(ord(location2[0]) + 1) + str((int(location2[1])) - 1))

    elif piece == 'KI':
        if (0 <= b - 1 < 8) and (0 <= a < 8) and board[b - 1][a].isupper() == False:
            possible_moves.append(location2[0] + str((int(location2[1])) + 1))
        if (0 <= b + 1 < 8) and (0 <= a < 8) and board[b + 1][a].isupper() == False:
            possible_moves.append(location2[0] + str((int(location2[1])) - 1))
        if (0 <= b < 8) and (0 <= a + 1 < 8) and board[b][a + 1].isupper() == False:
            possible_moves.append(chr(ord(location2[0]) + 1) + str((int(location2[1]))))
        if (0 <= b < 8) and (0 <= a - 1 < 8) and board[b][a - 1].isupper() == False:
            possible_moves.append(chr(ord(location2[0]) - 1) + str((int(location2[1]))))
        if (0 <= b - 1 < 8) and (0 <= a - 1 < 8) and board[b - 1][a - 1].isupper() == False:
            possible_moves.append(chr(ord(location2[0]) - 1) + str((int(location2[1])) + 1))
        if (0 <= b - 1 < 8) and (0 <= a + 1 < 8) and board[b - 1][a + 1].isupper() == False:
            possible_moves.append(chr(ord(location2[0]) + 1) + str((int(location2[1])) + 1))
        if (0 <= b + 1 < 8) and (0 <= a - 1 < 8) and board[b + 1][a - 1].isupper() == False:
            possible_moves.append(chr(ord(location2[0]) - 1) + str((int(location2[1])) - 1))
        if (0 <= b + 1 < 8) and (0 <= a + 1 < 8) and board[b + 1][a + 1].isupper() == False:
            possible_moves.append(chr(ord(location2[0]) + 1) + str((int(location2[1])) - 1))

    elif piece[0] == 'n':
        if (0 <= b - 2 < 8) and (0 <= a + 1 < 8) and board[b - 2][a + 1].islower() == False:
            possible_moves.append(chr(ord(location2[0]) + 1) + str((int(location2[1])) + 2))
        if (0 <= b - 2 < 8) and (0 <= a - 1 < 8) and board[b - 2][a - 1].islower() == False:
            possible_moves.append(chr(ord(location2[0]) - 1) + str((int(location2[1])) + 2))
        if (0 <= b - 1 < 8) and (0 <= a - 2 < 8) and board[b - 1][a - 2].islower() == False:
            possible_moves.append(chr(ord(location2[0]) - 2) + str((int(location2[1])) + 1))
        if (0 <= b - 1 < 8) and (0 <= a + 2 < 8) and board[b - 1][a + 2].islower() == False:
            possible_moves.append(chr(ord(location2[0]) + 2) + str((int(location2[1])) + 1))

        if (0 <= b + 2 < 8) and (0 <= a - 1 < 8) and board[b + 2][a - 1].islower() == False:
            possible_moves.append(chr(ord(location2[0]) - 1) + str((int(location2[1])) - 2))
        if (0 <= b + 2 < 8) and (0 <= a + 1 < 8) and board[b + 2][a + 1].islower() == False:
            possible_moves.append(chr(ord(location2[0]) + 1) + str((int(location2[1])) - 2))
        if (0 <= b + 1 < 8) and (0 <= a - 2 < 8) and board[b + 1][a - 2].islower() == False:
            possible_moves.append(chr(ord(location2[0]) - 2) + str((int(location2[1])) - 1))
        if (0 <= b + 1 < 8) and (0 <= a + 2 < 8) and board[b + 1][a + 2].islower() == False:
            possible_moves.append(chr(ord(location2[0]) + 2) + str((int(location2[1])) - 1))

        if (0 <= b + 1 < 8) and (0 <= a + 1 < 8) and board[b + 1][a + 1] == '  ':
            possible_moves.append(chr(ord(location2[0]) + 1) + str((int(location2[1])) - 1))
        if (0 <= b + 1 < 8) and (0 <= a - 1 < 8) and board[b + 1][a - 1] == '  ':
            possible_moves.append(chr(ord(location2[0]) - 1) + str((int(location2[1])) - 1))
        if (0 <= b - 1 < 8) and (0 <= a + 1 < 8) and board[b - 1][a + 1] == '  ':
            possible_moves.append(chr(ord(location2[0]) + 1) + str((int(location2[1])) + 1))
        if (0 <= b - 1 < 8) and (0 <= a - 1 < 8) and board[b - 1][a - 1] == '  ':
            possible_moves.append(chr(ord(location2[0]) - 1) + str((int(location2[1])) + 1))

    elif piece[0] == 'N':
        if (0 <= b - 2 < 8) and (0 <= a + 1 < 8) and board[b - 2][a + 1].isupper() == False:
            possible_moves.append(chr(ord(location2[0]) + 1) + str((int(location2[1])) + 2))
        if (0 <= b - 2 < 8) and (0 <= a - 1 < 8) and board[b - 2][a - 1].isupper() == False:
            possible_moves.append(chr(ord(location2[0]) - 1) + str((int(location2[1])) + 2))
        if (0 <= b - 1 < 8) and (0 <= a - 2 < 8) and board[b - 1][a - 2].isupper() == False:
            possible_moves.append(chr(ord(location2[0]) - 2) + str((int(location2[1])) + 1))
        if (0 <= b - 1 < 8) and (0 <= a + 2 < 8) and board[b - 1][a + 2].isupper() == False:
            possible_moves.append(chr(ord(location2[0]) + 2) + str((int(location2[1])) + 1))

        if (0 <= b + 2 < 8) and (0 <= a - 1 < 8) and board[b + 2][a - 1].isupper() == False:
            possible_moves.append(chr(ord(location2[0]) - 1) + str((int(location2[1])) - 2))
        if (0 <= b + 2 < 8) and (0 <= a + 1 < 8) and board[b + 2][a + 1].isupper() == False:
            possible_moves.append(chr(ord(location2[0]) + 1) + str((int(location2[1])) - 2))
        if (0 <= b + 1 < 8) and (0 <= a - 2 < 8) and board[b + 1][a - 2].isupper() == False:
            possible_moves.append(chr(ord(location2[0]) - 2) + str((int(location2[1])) - 1))
        if (0 <= b + 1 < 8) and (0 <= a + 2 < 8) and board[b + 1][a + 2].isupper() == False:
            possible_moves.append(chr(ord(location2[0]) + 2) + str((int(location2[1])) - 1))

        if (0 <= b + 1 < 8) and (0 <= a + 1 < 8) and board[b + 1][a + 1] == '  ':
            possible_moves.append(chr(ord(location2[0]) + 1) + str((int(location2[1])) - 1))
        if (0 <= b + 1 < 8) and (0 <= a - 1 < 8) and board[b + 1][a - 1] == '  ':
            possible_moves.append(chr(ord(location2[0]) - 1) + str((int(location2[1])) - 1))
        if (0 <= b - 1 < 8) and (0 <= a + 1 < 8) and board[b - 1][a + 1] == '  ':
            possible_moves.append(chr(ord(location2[0]) + 1) + str((int(location2[1])) + 1))
        if (0 <= b - 1 < 8) and (0 <= a - 1 < 8) and board[b - 1][a - 1] == '  ':
            possible_moves.append(chr(ord(location2[0]) - 1) + str((int(location2[1])) + 1))

    elif piece[0] == 'r':
        for c in range(1, 8):
            if (0 <= b < 8) and (0 <= a + c < 8) and (board[b][a + c].islower() == False):
                possible_moves.append(chr(ord(location2[0]) + c) + str((int(location2[1]))))
            if (0 <= b < 8) and (0 <= a + c < 8) and (board[b][a + c].islower() == True):
                break
            if (0 <= b < 8) and (0 <= a + c < 8) and board[b][a + c].isupper() == True:
                break
        for c in range(1, 8):
            if (0 <= b < 8) and (0 <= a - c < 8) and (board[b][a - c].islower() == False):
                possible_moves.append(chr(ord(location2[0]) - c) + str((int(location2[1]))))
            if (0 <= b < 8) and (0 <= a - c < 8) and (board[b][a - c].islower() == True):
                break
            if (0 <= b < 8) and (0 <= a - c < 8) and board[b][a - c].isupper() == True:
                break
        for c in range(1, 8):
            if (0 <= b + c < 8) and (0 <= a < 8) and board[b + c][a].islower() == False:
                possible_moves.append(chr(ord(location2[0])) + str((int(location2[1])) - c))
            if (0 <= b + c < 8) and (0 <= a < 8) and board[b + c][a].islower() == True:
                break
            if (0 <= b + c < 8) and (0 <= a < 8) and board[b + c][a].isupper() == True:
                break
        for c in range(1, 8):
            if (0 <= b - c < 8) and (0 <= a < 8) and board[b - c][a].islower() == False:
                possible_moves.append(chr(ord(location2[0])) + str((int(location2[1])) + c))
            if (0 <= b - c < 8) and (0 <= a < 8) and board[b - c][a].islower() == True:
                break
            if (0 <= b - c < 8) and (0 <= a < 8) and board[b - c][a].isupper() == True:
                break


    elif piece[0] == 'R':
        for c in range(1, 8):
            if (0 <= b < 8) and (0 <= a + c < 8) and board[b][a + c].isupper() == False:
                possible_moves.append(chr(ord(location2[0]) + c) + str((int(location2[1]))))
            if (0 <= b < 8) and (0 <= a + c < 8) and board[b][a + c].isupper() == True:
                break
            if (0 <= b < 8) and (0 <= a + c < 8) and board[b][a + c].islower() == True:
                break
        for c in range(1, 8):
            if (0 <= b < 8) and (0 <= a - c < 8) and board[b][a - c].isupper() == False:
                possible_moves.append(chr(ord(location2[0]) - c) + str((int(location2[1]))))
            if (0 <= b < 8) and (0 <= a - c < 8) and board[b][a - c].isupper() == True:
                break
            if (0 <= b < 8) and (0 <= a - c < 8) and board[b][a - c].islower() == True:
                break
        for c in range(1, 8):
            if (0 <= b + c < 8) and (0 <= a < 8) and board[b + c][a].isupper() == False:
                possible_moves.append(chr(ord(location2[0])) + str((int(location2[1])) - c))
            if (0 <= b + c < 8) and (0 <= a < 8) and board[b + c][a].isupper() == True:
                break
            if (0 <= b + c < 8) and (0 <= a < 8) and board[b + c][a].islower() == True:
                break
        for c in range(1, 8):
            if (0 <= b - c < 8) and (0 <= a < 8) and board[b - c][a].isupper() == False:
                possible_moves.append(chr(ord(location2[0])) + str((int(location2[1])) + c))
            if (0 <= b - c < 8) and (0 <= a < 8) and board[b - c][a].isupper() == True:
                break
            if (0 <= b - c < 8) and (0 <= a < 8) and board[b - c][a].islower() == True:
                break

    elif piece[0] == 'B':
        for c in range(1, 8):
            if (0 <= b + c < 8) and (0 <= a + c < 8) and board[b + c][a + c].isupper() == False:
                possible_moves.append(chr(ord(location2[0]) + c) + str((int(location2[1])) - c))
            if (0 <= b + c < 8) and (0 <= a + c < 8) and board[b + c][a + c].isupper() == True:
                break
            if (0 <= b + c < 8) and (0 <= a + c < 8) and board[b + c][a + c].islower() == True:
                break

        for c in range(1, 8):
            if (0 <= b + c < 8) and (0 <= a - c < 8) and board[b + c][a - c].isupper() == False:
                possible_moves.append(chr(ord(location2[0]) - c) + str((int(location2[1])) - c))
            if (0 <= b + c < 8) and (0 <= a - c < 8) and board[b + c][a - c].isupper() == True:
                break
            if (0 <= b + c < 8) and (0 <= a - c < 8) and board[b + c][a - c].islower() == True:
                break

    elif piece[0] == 'b':

        for c in range(1, 8):
            if (0 <= b - c < 8) and (0 <= a + c < 8) and board[b - c][a + c].islower() == False:
                possible_moves.append(chr(ord(location2[0]) + c) + str((int(location2[1])) + c))
            if (0 <= b - c < 8) and (0 <= a + c < 8) and board[b - c][a + c].islower() == True:
                break
            if (0 <= b - c < 8) and (0 <= a + c < 8) and board[b - c][a + c].isupper() == True:
                break

        for c in range(1, 8):
            if (0 <= b - c < 8) and (0 <= a - c < 8) and board[b - c][a - c].islower() == False:
                possible_moves.append(chr(ord(location2[0]) - c) + str((int(location2[1])) + c))
            if (0 <= b - c < 8) and (0 <= a - c < 8) and board[b - c][a - c].islower() == True:
                break
            if (0 <= b - c < 8) and (0 <= a - c < 8) and board[b - c][a - c].isupper() == True:
                break

    elif piece == 'qu':
        for c in range(1, 8):
            if (0 <= b + c < 8) and (0 <= a < 8) and board[b + c][a].islower() == False:
                possible_moves.append(chr(ord(location2[0])) + str((int(location2[1])) - c))
            if (0 <= b + c < 8) and (0 <= a < 8) and board[b + c][a].islower() == True:
                break
            if (0 <= b + c < 8) and (0 <= a < 8) and board[b + c][a].isupper() == True:
                break
        for c in range(1, 8):
            if (0 <= b < 8) and (0 <= a + c < 8) and (board[b][a + c].islower() == False):
                possible_moves.append(chr(ord(location2[0]) + c) + str((int(location2[1]))))
            if (0 <= b < 8) and (0 <= a + c < 8) and (board[b][a + c].islower() == True):
                break
            if (0 <= b < 8) and (0 <= a + c < 8) and board[b][a + c].isupper() == True:
                break
        for c in range(1, 8):
            if (0 <= b + c < 8) and (0 <= a + c < 8) and board[b + c][a + c].islower() == False:
                possible_moves.append(chr(ord(location2[0]) + c) + str((int(location2[1])) - c))
            if (0 <= b + c < 8) and (0 <= a + c < 8) and board[b + c][a + c].isupper() == True:
                break
            if (0 <= b + c < 8) and (0 <= a + c < 8) and board[b + c][a + c].islower() == True:
                break
        for c in range(1, 8):
            if (0 <= b + c < 8) and (0 <= a - c < 8) and board[b + c][a - c].islower() == False:
                possible_moves.append(chr(ord(location2[0]) - c) + str((int(location2[1])) - c))
            if (0 <= b + c < 8) and (0 <= a - c < 8) and board[b + c][a - c].isupper() == True:
                break
            if (0 <= b + c < 8) and (0 <= a - c < 8) and board[b + c][a - c].islower() == True:
                break
        for c in range(1, 8):
            if (0 <= b - c < 8) and (0 <= a + c < 8) and board[b - c][a + c].islower() == False:
                possible_moves.append(chr(ord(location2[0]) + c) + str((int(location2[1])) + c))
            if (0 <= b - c < 8) and (0 <= a + c < 8) and board[b - c][a + c].isupper() == True:
                break
            if (0 <= b - c < 8) and (0 <= a + c < 8) and board[b - c][a + c].islower() == True:
                break
        for c in range(1, 8):
            if (0 <= b - c < 8) and (0 <= a - c < 8) and board[b - c][a - c].islower() == False:
                possible_moves.append(chr(ord(location2[0]) - c) + str((int(location2[1])) + c))
            if (0 <= b - c < 8) and (0 <= a - c < 8) and board[b - c][a - c].isupper() == True:
                break
            if (0 <= b - c < 8) and (0 <= a - c < 8) and board[b - c][a - c].islower() == True:
                break
        for c in range(1, 8):
            if (0 <= b - c < 8) and (0 <= a < 8) and board[b - c][a].islower() == False:
                possible_moves.append(chr(ord(location2[0])) + str((int(location2[1])) + c))
            if (0 <= b - c < 8) and (0 <= a < 8) and board[b - c][a].islower() == True:
                break
            if (0 <= b - c < 8) and (0 <= a < 8) and board[b - c][a].isupper() == True:
                break
        for c in range(1, 8):
            if (0 <= b < 8) and (0 <= a - c < 8) and (board[b][a - c].islower() == False):
                possible_moves.append(chr(ord(location2[0]) - c) + str((int(location2[1]))))
            if (0 <= b < 8) and (0 <= a - c < 8) and (board[b][a - c].islower() == True):
                break
            if (0 <= b < 8) and (0 <= a - c < 8) and board[b][a - c].isupper() == True:
                break

    elif piece == 'QU':
        for c in range(1, 8):
            if (0 <= b + c < 8) and (0 <= a < 8) and board[b + c][a].isupper() == False:
                possible_moves.append(chr(ord(location2[0])) + str((int(location2[1])) - c))
            if (0 <= b + c < 8) and (0 <= a < 8) and board[b + c][a].isupper() == True:
                break
            if (0 <= b + c < 8) and (0 <= a < 8) and board[b + c][a].islower() == True:
                break
        for c in range(1, 8):
            if (0 <= b < 8) and (0 <= a + c < 8) and (board[b][a + c].isupper() == False):
                possible_moves.append(chr(ord(location2[0]) + c) + str((int(location2[1]))))
            if (0 <= b < 8) and (0 <= a + c < 8) and (board[b][a + c].isupper() == True):
                break
            if (0 <= b < 8) and (0 <= a + c < 8) and board[b][a + c].islower() == True:
                break
        for c in range(1, 8):
            if (0 <= b + c < 8) and (0 <= a + c < 8) and board[b + c][a + c].isupper() == False:
                possible_moves.append(chr(ord(location2[0]) + c) + str((int(location2[1])) - c))
            if (0 <= b + c < 8) and (0 <= a + c < 8) and board[b + c][a + c].islower() == True:
                break
            if (0 <= b + c < 8) and (0 <= a + c < 8) and board[b + c][a + c].isupper() == True:
                break
        for c in range(1, 8):
            if (0 <= b + c < 8) and (0 <= a - c < 8) and board[b + c][a - c].isupper() == False:
                possible_moves.append(chr(ord(location2[0]) - c) + str((int(location2[1])) - c))
            if (0 <= b + c < 8) and (0 <= a - c < 8) and board[b + c][a - c].islower() == True:
                break
            if (0 <= b + c < 8) and (0 <= a - c < 8) and board[b + c][a - c].isupper() == True:
                break
        for c in range(1, 8):
            if (0 <= b - c < 8) and (0 <= a + c < 8) and board[b - c][a + c].isupper() == False:
                possible_moves.append(chr(ord(location2[0]) + c) + str((int(location2[1])) + c))
            if (0 <= b - c < 8) and (0 <= a + c < 8) and board[b - c][a + c].islower() == True:
                break
            if (0 <= b - c < 8) and (0 <= a + c < 8) and board[b - c][a + c].isupper() == True:
                break
        for c in range(1, 8):
            if (0 <= b - c < 8) and (0 <= a - c < 8) and board[b - c][a - c].isupper() == False:
                possible_moves.append(chr(ord(location2[0]) - c) + str((int(location2[1])) + c))
            if (0 <= b - c < 8) and (0 <= a - c < 8) and board[b - c][a - c].islower() == True:
                break
            if (0 <= b - c < 8) and (0 <= a - c < 8) and board[b - c][a - c].isupper() == True:
                break
        for c in range(1, 8):
            if (0 <= b - c < 8) and (0 <= a < 8) and board[b - c][a].isupper() == False:
                possible_moves.append(chr(ord(location2[0])) + str((int(location2[1])) + c))
            if (0 <= b - c < 8) and (0 <= a < 8) and board[b - c][a].islower() == True:
                break
            if (0 <= b - c < 8) and (0 <= a < 8) and board[b - c][a].isupper() == True:
                break
        for c in range(1, 8):
            if (0 <= b < 8) and (0 <= a - c < 8) and (board[b][a - c].isupper() == False):
                possible_moves.append(chr(ord(location2[0]) - c) + str((int(location2[1]))))
            if (0 <= b < 8) and (0 <= a - c < 8) and (board[b][a - c].isupper() == True):
                break
            if (0 <= b < 8) and (0 <= a - c < 8) and board[b][a - c].islower() == True:
                break

    # print(possible_moves)
    for moves in possible_moves:
        if board[8 - int(moves[1])][ord(moves[0]) - 97] == "ki" or board[8 - int(moves[1])][ord(moves[0]) - 97] == "KI":
            possible_moves.remove(moves)
    if position not in possible_moves:
        print('FAILED')

    elif position in possible_moves:
        print('OK')
        for u in range(0, 8):
            for v in range(0, 8):
                if coordinates[u][v] == position:
                    board[u][v] = piece
                    board[b][a] = '  '

    # for l in board2:
    #   print(' '.join(l))


def Printo():  # prints the current board to the screen
    print(23 * "-")
    for l in board:
        print(' '.join(l))
    print(23 * "-")


for i in commands:
    if i[0][0] == "showmoves":
        print(">" + "showmoves {}".format((i[0][1][0] + i[0][1][1])))
        showmoves((i[0][1][0] + i[0][1][1]))
    elif i[0][0] == "move":
        print(">" + " move {} {}".format(i[0][1][0] + i[0][1][1], i[0][2][0] + i[0][2][1]))
        move(i[0][1][0] + i[0][1][1], i[0][2][0] + i[0][2][1])
    elif i[0][0] == "print":
        print(">" + "print")
        Printo()
    elif i[0][0] == "initialize":
        print(">" + "initialize")
        initialize()
    else:
        print(">" + "exit")
        sys.exit()