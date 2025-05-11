import os
from datetime import datetime

from app.application.interfaces.legal_petition_service import LegalPetitionService
from app.domain.entities.legal_petition import LegalPetition
from app.domain.entities.user_petition import UserPetition

import chromadb
from dotenv import load_dotenv
import google.generativeai as genai

load_dotenv()

DATA_PATH = r"../../data"
CHROMA_PATH = r"../../infra/chroma_db"
GOOGLE_API_KEY = os.getenv("GOOGLE_API_KEY")


class LegalPetitionServiceImpl(LegalPetitionService):

    @classmethod
    def generate_petition(cls, user_petition: UserPetition) -> LegalPetition:
        chroma_client = chromadb.PersistentClient(path=CHROMA_PATH)
        collection = chroma_client.get_or_create_collection(name="constitucion")

        results = collection.query(
            query_texts= [user_petition.description],
            n_results= 1
        )


        system_prompt = """
        Eres un abogado en el país de Colombia. 
        Genera una petición legal llamada Tutela, donde el único contenido que tendrá será una lista de HECHOS (en primera persona), FUNDAMENTOS JURíDICOS, y SOLICITUDES (en primera persona), a partir de la descripción y entidad suministrada.
        No generar ningún contenido adicional, únicamente las listas solicitadas.
        No incluir titulo de Tutela.
        Generar la Tutela basándose en los artículos de la Constitución Política de Colombia de 1991, incluyendo el artículo 86, el cual describe el mecanismo constitucional de la tutela.
        Omitir incluir información del usuario quien genera la tutela. 
        Únicamente incluir la entidad suministrada.
        
        Si no puedes generar la Tutela, responder: No se pudieron procesar los Hechos.
        
        Descripción:
        
        """+user_petition.description+"""
        
        Entidad:
        
        """+user_petition.institution+"""
        """


        model_name = "gemini-2.0-flash"
        genai.configure(api_key=os.getenv("GOOGLE_API_KEY"))

        model = genai.GenerativeModel('gemini-2.0-flash-001')
        response = model.generate_content(system_prompt)


        return LegalPetition(response.text, datetime.now())
 