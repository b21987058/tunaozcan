import ast
import sys
a = sys.argv[1]
try:
        if len(sys.argv) != 5:
                raise AssertionError
except AssertionError:
        print("Parameter number error")
        sys.exit()
try:
        assert sys.argv[1] == 'enc' or sys.argv[1] == 'dec'
except AssertionError:
        print("Undefined parameter error")
        sys.exit()
try:
        s= sys.argv[3]
        assert   s.find('plain_input.txt') != -1 or s.find('ciphertext.txt') != -1
except AssertionError:
        print("The input file could not be read error")
        sys.exit()
try:
        ss = sys.argv[2]
        sss = open(sys.argv[2],"r")
except FileNotFoundError:
        print("Key file not found error")
        sys.exit()
try:
        ssss = sys.argv[2]
        assert   s.find('key.txt') != -1
except AssertionError:
        print("The key file could not be read error")
        sys.exit()

values = {"A": 1, "B": 2, "C": 3, "D":4,"E":8,"F":6,"G":7,"H":8,"I":9,"J":10,"K":11,"L":12,
        "M":13,"N":14,"O":15,"P":16,"Q":17,"R":18,"S":19,"T":20,"U":21,"V":22,"W":23,"X":24,"Y":25,"Z":26,"-":27}
values2 = {1: "A", 2: "B", 3: "C", 4:"D",5:"E",6:"F",7:"G",8:"H",9:"I",10:"J",11:"K",12:"L",
        13:"M",14:"N",15:"O",16:"P",17:"Q",18:"R",19:"S",20:"T",21:"U",22:"V",23:"W",24:"X",25:"Y",26:"Z",27:"-"}
#g = open('key.txt',"r")
g = open(sys.argv[2],"r")
key = g.readlines()
key2 = []
key3 = [ [],[] ]

try:
        if len(key) == 0:
                raise AssertionError
except AssertionError:
        print("Key file is empty error:")
        sys.exit()

for element in key:
    key2.append(element.strip())

key2 = [list(ast.literal_eval(x)) for x in key2]


def enc():
        try:
                f = open(sys.argv[3], "r")
        except FileNotFoundError:
                print("Input file not found error")
                sys.exit()
        #f = open('plain_input.txt', "r")
        encodelancak = f.read()
        try:
                if len(encodelancak) == 0:
                        raise AssertionError
        except AssertionError:
                print("Input file is empty error")
                sys.exit()
        encodelancak2 = encodelancak.replace(' ','-')
        e = [ encodelancak2[i:i+len(key)] for i in range(0, len(encodelancak2), len(key)) ]

        a = 0
        if len(e[-1]) < len(key):
                a = len(key) - len(e[-1])
                e[-1] = e[-1]+ (a*('-'))

        values_of_letters = []
        values_of_letters2 = []
        for u in range(len(e)):
                for v in range(len(key)):
                        A = e[u][v]
                        #print(values[A])
                        values_of_letters.append(values[A])
        values_of_letters2 = [ values_of_letters[i:i+len(key)] for i in range(0, len(values_of_letters), len(key)) ]

        result=[]
        for i in range(len(values_of_letters2)):
                result.append([])
                for j in range(len(key2)):
                        result[i].append(0)

        for t in range(len(values_of_letters2)):
                for y in range(len(key2)):
                        for z in range(len(values_of_letters2[0])):
                                result[t][y] += key2[y][z] * values_of_letters2[t][z]

        result_string=""
        for i in range(len(result)):
                for j in range(len(result[0])):
                        result_string+=(str(result[i][j]))+str(',')
        result_string = result_string[:-1]

        #f2 = open('output_enc.txt',"w")
        f2 = open(sys.argv[4],"w")
        f2.write(result_string)

        f.close()
        f2.close()



def dec():
        #h = open('ciphertext.txt', "r")

        try:
                h = open(sys.argv[3], "r")
        except FileNotFoundError:
                print("Input file not found error")
                sys.exit()

        decyrptlencek = h.read()
        try:
                if len(decyrptlencek) == 0:
                        raise AssertionError
        except AssertionError:
                print("Input file is empty error")
                sys.exit()
        decyrptlencek = [int(s) for s in decyrptlencek.split(',')]
        decyrptlencek = [ decyrptlencek[ii:ii+len(key2)] for ii in range(0, len(decyrptlencek), len(key2)) ]
        if len(key2) == 2:
                key2[0][0], key2[1][1] = key2[1][1], key2[0][0]
                key2[0][1] = -(key2[0][1])
                key2[1][0] = -(key2[1][0])

                result = []
                for i in range(len(decyrptlencek)):
                        result.append([])
                        for j in range(len(key2)):
                                result[i].append(0)
                for t in range(len(decyrptlencek)):
                        for y in range(len(key2)):
                                for z in range(len(decyrptlencek[0])):
                                        result[t][y] += key2[y][z] * decyrptlencek[t][z]

                letters_of_values = []

                for u in range(len(result)):
                        for v in range(len(key)):
                                A = result[u][v]
                                # print(values[A])
                                letters_of_values.append(values2[A])
                letters_of_values_string=""
                for xx in range(len(letters_of_values)):
                        letters_of_values_string += letters_of_values[xx]
                letters_of_values_string = letters_of_values_string.replace('-', ' ')

                h2 = open('output_dec.txt',"w")
               # h2 = open(sys.argv[4],"w")
                h2.write(letters_of_values_string)

                h2.close()
                h.close()

        if  len(key2) == 3:

                determinant = 0
                determinant = (key2[0][0]*key2[1][1]*key2[2][2])+(key2[1][0]*key2[2][1]*key2[0][2])+(key2[2][0]*key2[0][1]*key2[1][2])-((key2[0][2]*key2[1][1]*key2[2][0])+(key2[1][2]*key2[2][1]*key2[0][0])+(key2[2][2]*key2[0][1]*key2[1][0]))

                key2[0][0],key2[0][1],key2[0][2],key2[1][0],key2[1][1],key2[1][2],key2[2][0],key2[2][1],key2[2][2] = (key2[1][1]*key2[2][2])-(key2[1][2]*key2[2][1]),(key2[1][0]*key2[2][2])-(key2[1][2]*key2[2][0]),(key2[1][0]*key2[2][1])-(key2[1][1]*key2[2][0]),(key2[0][1]*key2[2][2])-(key2[0][2]*key2[2][1]),(key2[0][0]*key2[2][2])-(key2[0][2]*key2[2][0]),(key2[0][0]*key2[2][1])-(key2[0][1]*key2[2][0]),(key2[0][1]*key2[1][2])-(key2[0][2]*key2[1][1]),(key2[0][0]*key2[1][2])-(key2[0][2]*key2[1][0]),(key2[0][0]*key2[1][1])-(key2[0][1]*key2[1][0]) #minör matris
                key2[0][0], key2[0][1], key2[0][2], key2[1][0], key2[1][1], key2[1][2], key2[2][0], key2[2][1], key2[2][2] = key2[0][0], -(key2[0][1]), key2[0][2], -(key2[1][0]), key2[1][1], -(key2[1][2]), key2[2][0], -(key2[2][1]), key2[2][2] #kofaktör matrisi
                key2[0][0], key2[0][1], key2[0][2], key2[1][0], key2[1][1], key2[1][2], key2[2][0], key2[2][1], key2[2][2] = key2[0][0], key2[1][0], key2[2][0], key2[0][1], key2[1][1], key2[2][1], key2[0][2], key2[1][2], key2[2][2]
                key2[0][0], key2[0][1], key2[0][2], key2[1][0], key2[1][1], key2[1][2], key2[2][0], key2[2][1], key2[2][2] = key2[0][0]/determinant, key2[0][1]/determinant, key2[0][2]/determinant, key2[1][0]/determinant, key2[1][1]/determinant, key2[1][2]/determinant, key2[2][0]/determinant, key2[2][1]/determinant, key2[2][2]/determinant
                for xyz in range(len(key2)):
                        for xxx in range(len(key2[0])):
                                key2[xyz][xxx] = int(key2[xyz][xxx])
                result = []
                for i in range(len(decyrptlencek)):
                        result.append([])
                        for j in range(len(key2)):
                                result[i].append(0)
                for t in range(len(decyrptlencek)):
                        for y in range(len(key2)):
                                for z in range(len(decyrptlencek[0])):
                                        result[t][y] += key2[y][z] * decyrptlencek[t][z]
                letters_of_values = []

                for u in range(len(result)):
                        for v in range(len(key)):
                                A = result[u][v]
                                # print(values[A])
                                letters_of_values.append(values2[A])
                letters_of_values_string = ""
                for xx in range(len(letters_of_values)):
                        letters_of_values_string += letters_of_values[xx]
                letters_of_values_string = letters_of_values_string.replace('-', ' ')

                h2 = open('output_dec.txt', "w")
                # h2 = open(sys.argv[4],"w")
                h2.write(letters_of_values_string)

                h2.close()
                h.close()


if a == 'dec':
        dec()
elif a == 'enc':
        enc()

g.close()
#print(key)
#print(key2)
#print(determinant)
#print(decyrptlencek)
#print(result)
#print(letters_of_values)
#print(letters_of_values_string)
