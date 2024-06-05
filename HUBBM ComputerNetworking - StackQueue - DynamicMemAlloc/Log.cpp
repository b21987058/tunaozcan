//
// Created by alperen on 2.10.2023.
//

#include "Log.h"

Log::Log(const string &_timestamp, const string &_message, int _number_of_frames, int _number_of_hops, const string &_sender_id,
         const string &_receiver_id, bool _success, ActivityType _type) {
    timestamp = _timestamp;
    message_content = _message;
    number_of_frames = _number_of_frames;
    number_of_hops = _number_of_hops;
    sender_id = _sender_id;
    receiver_id = _receiver_id;
    success_status = _success;
    activity_type = _type;
}

string Log::activity_to_string(ActivityType activity)
{
    switch (activity) {
        case ActivityType::MESSAGE_RECEIVED: return "Message Received";
        case ActivityType::MESSAGE_FORWARDED: return "Message Forwarded";
        case ActivityType::MESSAGE_SENT: return "Message Sent";
        case ActivityType::MESSAGE_DROPPED: return "Message Dropped";
        default: return "Unknown Activity";
    }
}

void Log::print()
{
    cout << "Activity: " << activity_to_string(activity_type) << endl;
    cout << "Timestamp: " << timestamp << endl;
    cout << "Number of frames: " << number_of_frames << endl;
    cout << "Number of hops: " << number_of_hops << endl;
    cout << "Sender ID: " << sender_id << endl;
    cout << "Receiver ID: " << receiver_id << endl;
    cout << "Success: " << (success_status ? "Yes" : "No") << endl;

    // Print message content if the activity is MESSAGE_RECEIVED or MESSAGE_SENT
    if (activity_type == ActivityType::MESSAGE_RECEIVED || activity_type == ActivityType::MESSAGE_SENT) {
        cout << "Message: \"" << message_content << "\"" << endl;
    }
}
Log::~Log() {
    // TODO: Free any dynamically allocated memory if necessary.
}
