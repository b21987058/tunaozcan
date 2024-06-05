// EdgeDetector.cpp

#include "EdgeDetector.h"
#include <cmath>



// Default constructor
EdgeDetector::EdgeDetector() {
     gx = new double*[3];
    gx[0] = new double[3]{-1.0, 0.0, 1.0};
    gx[1] = new double[3]{-2.0, 0.0, 2.0};
    gx[2] = new double[3]{-1.0, 0.0, 1.0};

    gy = new double*[3];
    gy[0] = new double[3]{-1.0, -2.0, -1.0};
    gy[1] = new double[3]{0.0, 0.0, 0.0};
    gy[2] = new double[3]{1.0, 2.0, 1.0};
}


// Destructor
EdgeDetector::~EdgeDetector() {
    for (int i=0;i<3;i++){
        delete [] gx[i];
    }
    delete[] gx;
    for (int i=0;i<3;i++){
        delete [] gy[i];
    }
    delete[] gy;
}

// Detect Edges using the given algorithm
std::vector<std::pair<int, int>> EdgeDetector::detectEdges(const ImageMatrix& input_image) {
    Convolution cov1(gx, 3, 3, 1, true);
    Convolution cov2(gy, 3, 3, 1, true);

    ImageMatrix Ix = cov1.convolve(input_image);
    ImageMatrix Iy = cov2.convolve(input_image);

    double sum = 0;
    double counter = 0;
    double threshold = 0;

    for (int i = 0; i < Iy.get_height(); i++) {
        for (int j = 0; j < Iy.get_width(); j++) {
            sum += sqrt((Ix.get_data()[i][j] * Ix.get_data()[i][j]) + (Iy.get_data()[i][j] * Iy.get_data()[i][j]));
            counter += 1;
        }
    }

    threshold = sum / counter;
    int gm = 0;
    std::vector<std::pair<int, int>> EdgePixels;

    for (int i = 0; i < Iy.get_height(); i++) {
        for (int j = 0; j < Iy.get_width(); j++) {
            gm = sqrt((Ix.get_data()[i][j] * Ix.get_data()[i][j]) + (Iy.get_data()[i][j] * Iy.get_data()[i][j]));
            if (gm > threshold) {
                EdgePixels.push_back(std::make_pair(i, j));
            }
        }
    }

    return EdgePixels;
}
