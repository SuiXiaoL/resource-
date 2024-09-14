'''
文档数据（txt、pdf、csv、docx）分割、向量化、存储
'''
import shutup
shutup.please()

from utils import *

import os
from glob import glob
from langchain.vectorstores.chroma import Chroma
from langchain.document_loaders import CSVLoader, PyMuPDFLoader, TextLoader, UnstructuredWordDocumentLoader
from langchain.text_splitter import RecursiveCharacterTextSplitter


def doc2vec():
    # 1 定义文本分割器
    text_splitter = RecursiveCharacterTextSplitter(
        chunk_size=300,  # 文本块长度
        chunk_overlap=50  # 重叠长度
    )

    # 2 读取并分割文件
    dir_path = os.path.join(os.path.dirname(__file__), './data/inputs/')
    documents = []
    for file_path in glob(dir_path + '*.*'):
        loader = None
        if '.csv' in file_path:
            loader = CSVLoader(file_path, encoding='utf-8')
        if '.pdf' in file_path:
            loader = PyMuPDFLoader(file_path)
        if '.txt' in file_path:
            loader = TextLoader(file_path, encoding='utf-8')
        if '.docx' in file_path:
            loader = UnstructuredWordDocumentLoader(file_path, encoding='utf-8')
        if loader:
            documents += loader.load_and_split(text_splitter)
    # print(documents)

    # 3 向量化并存储 使用 ChromaDB
    # Faiss 常用来存储句向量；ChromaDB 可用来存储原始文件，两者用法相似
    if documents:
        vdb = Chroma(
            embedding_function=get_embeddings_model(),
            persist_directory=os.path.join(os.path.dirname(__file__), './data/db/')
        )
        chunk_size = 10
        for i in range(0, len(documents), chunk_size):
            texts = [doc.page_content for doc in documents[i:i + chunk_size]]
            metadatas = [doc.metadata for doc in documents[i:i + chunk_size]]
            vdb.add_texts(texts, metadatas)
        vdb.persist()

        # vdb = Chroma.from_documents(
        #     documents = documents, 
        #     embedding = get_embeddings_model(),
        #     persist_directory = os.path.join(os.path.dirname(__file__), './data/db/')
        # )
        # vdb.persist()


if __name__ == '__main__':
    doc2vec()
