import random

# 可以自定义或扩展这个单词库
WORD_LIST = [
    'apple', 'banana', 'orange', 'grape', 'peach', 'pear', 'plum', 'melon', 'kiwi', 'lemon',
    'cat', 'dog', 'fish', 'bird', 'horse', 'sheep', 'tiger', 'lion', 'bear', 'wolf',
    'table', 'chair', 'desk', 'sofa', 'bed', 'lamp', 'door', 'window', 'floor', 'ceiling',
    'computer', 'phone', 'mouse', 'keyboard', 'screen', 'printer', 'camera', 'speaker', 'router', 'cable',
    'book', 'pen', 'pencil', 'paper', 'notebook', 'bag', 'clock', 'watch', 'calendar', 'map',
    'river', 'mountain', 'forest', 'ocean', 'lake', 'island', 'valley', 'desert', 'beach', 'hill',
    'happy', 'sad', 'angry', 'excited', 'bored', 'tired', 'scared', 'brave', 'calm', 'proud',
    'run', 'walk', 'jump', 'swim', 'fly', 'drive', 'ride', 'climb', 'crawl', 'dance',
    'red', 'blue', 'green', 'yellow', 'black', 'white', 'purple', 'brown', 'pink', 'gray',
    'spring', 'summer', 'autumn', 'winter', 'rain', 'snow', 'wind', 'cloud', 'sun', 'moon'
]

def generate_words(n=10):
    return random.sample(WORD_LIST, n)

if __name__ == "__main__":
    words = generate_words(10)
    print("请背诵以下单词：")
    for i, word in enumerate(words, 1):
        print(f"{i}. {word}") 