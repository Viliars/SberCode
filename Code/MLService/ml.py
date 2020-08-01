from random import randint


def classify(text: str):
    return 1000 + randint(1, 20)

def save_to_retrain(text:str, rating: int, right_class: int):
    # TODO Save to Hadoop/SQL
    with open("retrain.csv", "a") as fout:
        print(text, rating, right_class, sep=',', file=fout)