#include "EncodeMessage.h"
#include <cmath>



// Default Constructor
EncodeMessage::EncodeMessage() {

}

// Destructor
EncodeMessage::~EncodeMessage() {
    
}

// Function to encode a message into an image matrix
ImageMatrix EncodeMessage::encodeMessageToImage(const ImageMatrix &img, const std::string &message, const std::vector<std::pair<int, int>>& positions) {
 std::string modifiedMessage = "";
 
 
    for (int i = 0; i < message.length(); i++) {
        char originalChar = message[i];
        int index = i;
        int fibonacciNumber = 0;
        if (isPrime(index) ) {
            fibonacciNumber = fibonacci(index);
        }else{
            fibonacciNumber=0;
        }
        char transformedChar = transformChar(originalChar, fibonacciNumber);
        modifiedMessage += transformedChar;
    }

    
    int position_holder=0;
    double ** newpt=img.get_data();
    std::string shiftedM=rightCircularShift(modifiedMessage);
    std::string binaryString =stringToBinary(shiftedM); 
    for(int i = 0; i < binaryString.length(); i++) {
        int x= positions[position_holder].first;
        int y= positions[position_holder].second;
        position_holder++;
        int value=static_cast<int>(newpt[x][y]);
        int lowestBit = value & 1;
        if (!binaryString.empty() && binaryString[i] == '1') {
            value |= 1;
        } else {
            value &= ~1;
        }
        
        double doubleValue = static_cast<double>(value);
        newpt[x][y]=doubleValue;

    }
    
    return  ImageMatrix(newpt, img.get_height(), img.get_width());;
}
bool EncodeMessage::isPrime(int number) {
    if (number <= 1) {
        return false; 
    }

    if (number <= 3) {
        return true; 
    }

    
    if (number % 2 == 0 || number % 3 == 0) {
        return false;
    }

    
    for (int i = 5; i * i <= number; i += 6) {
        if (number % i == 0 || number % (i + 2) == 0) {
            return false;
        }
    }

    return true;
}
char EncodeMessage::transformChar(char c, int index) {
    int asciiValue = static_cast<int>(c);
    asciiValue = index+asciiValue;
    if (asciiValue <= 32) {
        asciiValue = 33 + asciiValue; 
    } else if (asciiValue >= 127) {
        asciiValue = 126;  // 127'den büyükse 126 yapın
    }
    return static_cast<char>(asciiValue);
}
int EncodeMessage::fibonacci(int n){

    if (n <= 0) return 0;
    if (n == 1) return 1;
    return fibonacci(n - 1) + fibonacci(n - 2);

}
std::string EncodeMessage::rightCircularShift(const std::string &message) {
    int shift = message.length() / 2;
    int len = message.length();
    shift = shift % len;
    return message.substr(len - shift) + message.substr(0, len - shift);
}
std::vector<int> EncodeMessage::assignIndexesToPrimes(const std::string &message) {
    std::vector<int> indexes;
    
    int prime = 2;
    for (size_t i = 0; i < message.length(); ++i) {
        while (true) {
            bool isPrime = true;
            for (int j = 2; j * j <= prime; ++j) {
                if (prime % j == 0) {
                    isPrime = false;
                    break;
                }
            }
            if (isPrime) {
                break;
            }
            prime++; 
        }
        indexes.push_back(prime);
        prime++; 
    }

    return indexes;
}
std::string EncodeMessage::charToBinary(char c) {
    std::string binaryString;
    for (int i = 7; i >= 0; --i) {
        binaryString += ((c >> i) & 1) ? '1' : '0';
    }
    return binaryString;
}
std::string EncodeMessage::stringToBinary(const std::string& input) {
    std::string binaryString;
    
    for (char c : input) {
        for (int i = 7; i >= 0; --i) {
            binaryString += ((c >> i) & 1) ? '1' : '0';
        }
    }
    
    return binaryString;
}