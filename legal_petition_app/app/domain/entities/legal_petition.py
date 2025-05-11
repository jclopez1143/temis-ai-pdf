from datetime import datetime

class LegalPetition:
    content: str
    date: datetime

    def __init__(self, content: str, date: datetime):
        self.content = content
        self.date = date