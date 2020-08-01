from typing import List
from pydantic import BaseModel


class Сlassify(BaseModel):
    text: str
    rating: int
    uuid: str

class MissClass(BaseModel):
    text: str
    rating: int
    right_class: int
    uuid: str

