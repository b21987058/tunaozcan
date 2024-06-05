#include "ImageSharpening.h"

// Default constructor
ImageSharpening::ImageSharpening() {
     kernel_height = 3;
     kernel_width = 3;
    blurring_kernel = new double*[kernel_height];

    for (int i = 0; i < kernel_height; i++) {
        blurring_kernel[i] = new double[kernel_width];

        for (int j = 0; j < kernel_width; j++) {
            blurring_kernel[i][j] = 1.0 / 9.0; 
        }
    }
}

ImageSharpening::~ImageSharpening() {
    for (int i = 0; i < kernel_height; i++) {
        delete[] blurring_kernel[i];
    }
    delete[] blurring_kernel;
    kernel_height = 0;
    kernel_width = 0;
}

ImageMatrix ImageSharpening::sharpen(const ImageMatrix& input_image, double k=2) {
    Convolution conv(blurring_kernel, kernel_height, kernel_width, 1, true);
    ImageMatrix blurred_image = conv.convolve(input_image);
    ImageMatrix sharpImg = input_image + ((input_image - blurred_image) * k);
    double ** tmp=sharpImg.get_data();
    for (int i = 0; i < sharpImg.get_height(); i++) {
    for (int j = 0; j < sharpImg.get_width(); j++) {
            if (tmp[i][j] < 0.0) {
               tmp[i][j] = 0.0;
        } else if (tmp[i][j] > 255.0) {
                tmp[i][j] = 255.0;
            }
        }
    }
    ImageMatrix newSharpened(tmp,sharpImg.get_height(),sharpImg.get_width());
    return newSharpened;
}