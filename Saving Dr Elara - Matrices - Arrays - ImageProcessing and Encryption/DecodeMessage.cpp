// DecodeMessage.cpp

#include "DecodeMessage.h"
#include <iostream>

// Default constructor
DecodeMessage::DecodeMessage() {
    // Nothing specific to initialize here
}

// Destructor
DecodeMessage::~DecodeMessage() {
    // Nothing specific to clean up
}


std::string DecodeMessage::decodeFromImage(const ImageMatrix& image, const std::vector<std::pair<int, int>>& edgePixels) {
        std::string binaryString;
        std::string message;
        std::string byte;

        for (const auto& pixel : edgePixels) {
            int pixelValue = static_cast<int>(image.get_data()[pixel.first][pixel.second]);
            int lsb = pixelValue & 1; // En anlamsız biti al
            binaryString.push_back('0' + lsb);
        }

        
        while (binaryString.length() % 7 != 0) {
            binaryString.insert(binaryString.begin(), '0');
        }

    
        for (int  i = 0; i < binaryString.length(); i += 7) {
            std::string byte = binaryString.substr(i, 7);
            int asciiValue = std::stoi(byte, nullptr, 2);

            // ASCII değerini düzeltme
            if (asciiValue <= 32) {
                asciiValue += 33;
            }
            else if (asciiValue == 127) {
                asciiValue = 126;
            }

            char character = static_cast<char>(asciiValue);
            message.push_back(character);
        }

        return message;
    }

