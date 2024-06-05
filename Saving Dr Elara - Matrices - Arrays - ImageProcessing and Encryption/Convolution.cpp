#include <iostream>
#include <cmath>
#include "Convolution.h"

// Default constructor 
Convolution::Convolution() {
    kh = 0;
    kw = 0;
    stride_val = 1;
    pad = false;
    customKernel = nullptr;
}

// Parametrized constructor for custom kernel and other parameters
Convolution::Convolution(double** customKernel, int kh, int kw, int stride_val, bool pad) {
    this->kh = kh;
    this->kw = kw;
    this->stride_val = stride_val;
    this->pad = pad;
    
    this->customKernel = new double*[kh];
    for (int i = 0; i < kh; i++) {
        this->customKernel[i] = new double[kw];
        for (int j = 0; j < kw; j++) {
            this->customKernel[i][j] = customKernel[i][j];
        }
    }
}

// Destructor
Convolution::~Convolution() {
    for (int i = 0; i < kh; i++) {
        delete[] customKernel[i];
    }
    delete[] customKernel;
    customKernel = nullptr;
}

// Copy constructor
Convolution::Convolution(const Convolution &other) {
    kh = other.kh;
    kw = other.kw;
    stride_val = other.stride_val;
    pad = other.pad;
    
    customKernel = new double*[kh];
    for (int i = 0; i < kh; i++) {
        customKernel[i] = new double[kw];
        for (int j = 0; j < kw; j++) {
            customKernel[i][j] = other.customKernel[i][j];
        }
    }
    std::cout << "Copy constructor called." << std::endl;
}

// Copy assignment operator
Convolution& Convolution::operator=(const Convolution &other) {
    if (this == &other) {
        return *this;
    }
    
    for (int i = 0; i < kh; i++) {
        delete[] customKernel[i];
    }
    delete[] customKernel;

    kh = other.kh;
    kw = other.kw;
    stride_val = other.stride_val;
    pad = other.pad;
    
    customKernel = new double*[kh];
    for (int i = 0; i < kh; i++) {
        customKernel[i] = new double[kw];
        for (int j = 0; j < kw; j++) {
            customKernel[i][j] = other.customKernel[i][j];
        }
    }
    return *this;
}

// Convolve Function: Responsible for convolving the input image with a kernel and return the convolved image.
ImageMatrix Convolution::convolve(const ImageMatrix& input_image) const {
    int inputH = input_image.get_height();
    int inputW = input_image.get_width();
    int outputH, outputW;

    if (pad) {
        outputH = std::floor((inputH + 2 - kh) / stride_val) + 1;
        outputW = std::floor((inputW + 2 - kw) / stride_val) + 1;
    } else {
        outputH = std::floor((inputH - kh) / stride_val) + 1;
        outputW = std::floor((inputW - kw) / stride_val) + 1;
    }
    
    double **output = new double*[outputH];
    for (int i = 0; i < outputH; i++) {
        output[i] = new double[outputW];
    }
    
    // Initialize the output matrix with zeros
    
    for (int i = 0; i < outputH; i++) {
        for (int j = 0; j < outputW; j++) {
            output[i][j] = 0.0;
        }
    }

    // Convolution
    for (int i = 0; i < outputH; i++) {
        for (int j = 0; j < outputW; j++) {
            for (int m = 0; m < kh; m++) {
                for (int n = 0; n < kw; n++) {
                    int inputRow = i * stride_val + m;
                    int inputCol = j * stride_val + n;

                    if (inputRow < 0 || inputRow >= inputH || inputCol < 0 || inputCol >= inputW) {
                        continue;
                    }

                    output[i][j] += input_image.get_data()[inputRow][inputCol] * customKernel[m][n];
                }
            }
        }
    }
    
    ImageMatrix ret(output, outputH, outputW);
    return ret;
}
