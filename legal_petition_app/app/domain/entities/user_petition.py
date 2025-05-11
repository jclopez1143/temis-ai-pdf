from pydantic import BaseModel, Field

class UserPetition(BaseModel):
    institution: str = Field(min_length=8)
    description: str = Field(min_length=50, max_length=3000)

def __init__(self, institution: str, description: str):
    self.institution = institution
    self.description = description