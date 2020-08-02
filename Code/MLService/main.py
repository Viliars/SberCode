from fastapi import FastAPI
from fastapi.responses import FileResponse, JSONResponse
from fastapi.encoders import jsonable_encoder
from typing import List
from models import Сlassify, MissClass
from ml import *

app = FastAPI()

@app.get("/class")
def root(requests: List[Сlassify]):
    answer = []
    for request in requests:
        result, result_ton = classify(request.text)
        answer.append({
            "uuid": request.uuid,
            "class": result,
            "class_ton": result_ton
        })

    response = jsonable_encoder(answer)

    return JSONResponse(content=response)

@app.get("/missclass")
def miss(requests: List[MissClass]):
    for request in requests:
        save_to_retrain(request.text, request.rating, request.right_class)

    return "OK"
