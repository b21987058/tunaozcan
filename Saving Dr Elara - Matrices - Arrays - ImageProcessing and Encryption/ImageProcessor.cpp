#include "ImageProcessor.h"

ImageProcessor::ImageProcessor() {

}

ImageProcessor::~ImageProcessor() {

}



std::string ImageProcessor::decodeHiddenMessage(const ImageMatrix &img) {
    ImageSharpening sharpeningModule;
    EdgeDetector edgeDetectionModule;
    DecodeMessage messageDecodingModule;
    ImageMatrix sharpenedImage = sharpeningModule.sharpen(img,2);

    std::vector<std::pair<int, int>> positions = edgeDetectionModule.detectEdges(sharpenedImage);

    std::string hiddenMessage = messageDecodingModule.decodeFromImage(sharpenedImage,positions);

    return hiddenMessage;
}

ImageMatrix ImageProcessor::encodeHiddenMessage(const ImageMatrix &img, const std::string &message) {
    ImageSharpening sharpeningModule;
    EdgeDetector edgeDetectionModule;
    EncodeMessage messageEncodingModule;
    ImageMatrix sharpenedImage = sharpeningModule.sharpen(img,2);

    std::vector<std::pair<int, int>> positions = edgeDetectionModule.detectEdges(sharpenedImage);

    ImageMatrix encodedImage = messageEncodingModule.encodeMessageToImage(sharpenedImage, message,positions);

    return encodedImage;
}
