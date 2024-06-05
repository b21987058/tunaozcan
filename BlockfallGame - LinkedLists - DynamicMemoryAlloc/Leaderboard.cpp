#include "Leaderboard.h"
#include <fstream>
#include <iomanip>
#include <iostream>
void Leaderboard::insert_new_entry(LeaderboardEntry *new_entry)
{
    if (head_leaderboard_entry == nullptr || new_entry->score >= head_leaderboard_entry->score)
    {
        // Eğer liste boşsa veya yeni girişin skoru en yüksekse, başa eklenir.
        new_entry->next_leaderboard_entry = head_leaderboard_entry;
        head_leaderboard_entry = new_entry;
    }
    else
    {
        LeaderboardEntry *current_entry = head_leaderboard_entry;
        LeaderboardEntry *prev_entry = nullptr;

        while (current_entry != nullptr && new_entry->score < current_entry->score)
        {
            prev_entry = current_entry;
            current_entry = current_entry->next_leaderboard_entry;
        }

        // Diğer durumda yeni girişi uygun konuma ekler.
        prev_entry->next_leaderboard_entry = new_entry;
        new_entry->next_leaderboard_entry = current_entry;
    }

    // Gerekiyorsa liste boyutunu kontrol et ve en fazla 10 girişi koru.
    int entry_count = 0;
    LeaderboardEntry *temp_entry = head_leaderboard_entry;
    LeaderboardEntry *prev_temp_entry = nullptr;

    while (temp_entry != nullptr && entry_count < MAX_LEADERBOARD_SIZE)
    {
        prev_temp_entry = temp_entry;
        temp_entry = temp_entry->next_leaderboard_entry;
        entry_count++;
    }

    if (entry_count >= MAX_LEADERBOARD_SIZE)
    {
        // Liste boyutunu aştıysak, fazla girişleri sil.
        if (prev_temp_entry != nullptr)
        {
            prev_temp_entry->next_leaderboard_entry = nullptr;
        }

        // temp_entry şu anda 10. girişi gösteriyor, bu yüzden sonraki girişi silmeliyiz.
        delete temp_entry;
    }
}
void Leaderboard::write_to_file(const string &filename)
{
    ofstream file(filename);

    if (file.is_open())
    {
        LeaderboardEntry *current_entry = head_leaderboard_entry;

        while (current_entry != nullptr)
        {
            file << current_entry->score << " " << current_entry->last_played << " " << current_entry->player_name << endl;
            current_entry = current_entry->next_leaderboard_entry;
        }

        file.close();
    }
}

void Leaderboard::read_from_file(const string &filename)
{
    ifstream file(filename);

    if (file.is_open())
    {
        while (head_leaderboard_entry != nullptr)
        {
            LeaderboardEntry *temp_entry = head_leaderboard_entry;
            head_leaderboard_entry = head_leaderboard_entry->next_leaderboard_entry;
            delete temp_entry;
        }

        // Dosyadan okuma işlemi
        unsigned long score;
        time_t last_played;
        string player_name;

        while (file >> score >> last_played >> player_name)
        {
            LeaderboardEntry *new_entry = new LeaderboardEntry(score, last_played, player_name);
            insert_new_entry(new_entry); // Eklenen giriş, uygun konuma eklenir.
        }

        file.close();
    }
}

void Leaderboard::print_leaderboard()
{
    std::cout << "Leaderboard" << std::endl;
    std::cout << "-----------" << std::endl;

    LeaderboardEntry *current_entry = head_leaderboard_entry;
    int rank = 1;

    while (current_entry != nullptr)
    {
        std::cout << std::setw(2) << rank << ". ";
        std::cout << std::setw(14) << current_entry->player_name << " ";
        std::cout << std::setw(4) << current_entry->score << " ";
        std::cout << std::put_time(std::localtime(&current_entry->last_played), "%T/%d.%m.%Y") << std::endl;

        current_entry = current_entry->next_leaderboard_entry;
        rank++;
    }

    std::cout << std::endl;
}
int Leaderboard::getHighScore()  {
        if (head_leaderboard_entry != nullptr) {
            return head_leaderboard_entry->score;
        } else {
            // Eğer liderlik tablosu boşsa, high score'u sıfır olarak kabul edebiliriz.
            return 0;
        }
    }
Leaderboard::~Leaderboard()
{
      LeaderboardEntry* current_entry = head_leaderboard_entry;

    while (current_entry != nullptr) {
        LeaderboardEntry* temp_entry = current_entry;
        current_entry = current_entry->next_leaderboard_entry;
        delete temp_entry;
    }
    
    // Liderlik tablosunu boşalt
    head_leaderboard_entry = nullptr;

   

}
