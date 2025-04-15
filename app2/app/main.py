from fastapi import FastAPI, HTTPException
from app.services import CalculadoraService
from app.exceptions import HuanaException

app = FastAPI()

# Instancia del servicio
calculadora_service = CalculadoraService()

# Endpoint para multiplicar


@app.get("/calculadora/multiplicar/{a}/{b}")
def multiplicar(a: float, b: float):
    try:
        resultado = calculadora_service.multiplicar(a, b)
        return {"resultado": resultado}
    except HuanaException as e:
        raise HTTPException(status_code=400, detail=f"NO NO {e}")
    except ZeroDivisionError as e:
        raise HTTPException(status_code=422, detail=f"ERROR ARITMÉTICO: {e}")
    except Exception as e:
        raise HTTPException(status_code=500, detail=f"ERROR INTERNO: {e}")

# Endpoint para sumar


@app.get("/calculadora/sumar/{a}/{b}")
def sumar(a: float, b: float):
    try:
        resultado = calculadora_service.sumar(a, b)
        return {"resultado": resultado}
    except HuanaException as e:
        raise HTTPException(status_code=400, detail=f"NO NO {e}")


@app.get("/favicon.ico")
def favicon():
    return {"favicon": "https://www.fastapi.tiangolo.com/img/favicon.png"}
