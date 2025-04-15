class CalculadoraService:
    def multiplicar(self, a: float, b: float) -> float:
        if a is None or b is None:
            raise ValueError("Los números no pueden ser nulos")
        return a * b

    def sumar(self, a: float, b: float) -> float:
        if a is None or b is None:
            raise ValueError("Los números no pueden ser nulos")
        return a + b
