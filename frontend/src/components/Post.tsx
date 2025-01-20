import React, { ChangeEvent, useState } from "react";
import { validateImage } from "image-validator";
import { ref, uploadBytes } from "firebase/storage";
import { db, storage } from "../lib/firebase";
import { addDoc, collection, DocumentReference, updateDoc } from "firebase/firestore";
import Image from "../types/image";


const Post: React.FC = () => {
    const [file, setFile] = useState<File | null>(null);
    const [text, setText] = useState<string>("");
    const [imagePreview, setImagePreview] = useState<string | null>(null);
    const [errorMsg, setErrorMsg] = useState<string | null>(null);

    
    // 画像ファイルかつ3GB以下であるかの判定を行う．
    const validateFile = async (selectedFile: File): Promise<boolean> => {
        const limitFileSize = 3 * 1024 * 1024;

        if(selectedFile.size > limitFileSize){
            setErrorMsg("File size is too large, please keep it under 3 GB.");
            return false;
        }

        const isValidImage = await validateImage(selectedFile);

        if(!isValidImage){
            setErrorMsg("you cannot upload anything other than image files.");
            return false;
        }

        return true;
    };

    // 画像を選択する
    const handleImageSelect = async(e: ChangeEvent<HTMLInputElement>) => {
        setErrorMsg(null);
        e.preventDefault();
        const selectedFile = e.target.files?.[0];

        if(selectedFile && (await validateFile(selectedFile))){
            const reader = new FileReader();

            reader.onloadend = () => {
                setFile(selectedFile);
                setImagePreview(reader.result as string);
                setErrorMsg(null);
            }

            reader.readAsDataURL(selectedFile);
        }
    };

    // 画像をStorageにアップロードし，firestoreに保存する
    const uploadImage = async () => {
        try{
            if(!file) {
                setErrorMsg("File not seleted.");
                return;
            }

            // firestoreにダミーデータをアップロードし，ユニークな識別番号を生成
            const dummyData: Image = {
                filePath: "dymmy",
                text: "dymmy",
                timestamp: new Date(),
                greyFilePath: "dymmy"
            };
            const docRef: DocumentReference = await addDoc(collection(db, "Images"), dummyData);
            const docId = docRef.id;

            //　storageへの保存パスを作成
            const extension = file.name.split(".").pop();
            const filePath: string = `images/${docId}/${docId}.${extension}`;

            const timestamp = new Date().getTime();

            const image:Image = {
                filePath: filePath,
                text: text,
                timestamp: timestamp,
                greyFilePath: null,
            }
            
            // storageにアップロード
            const storageRef = ref(storage, filePath);
            await uploadBytes(storageRef, file);

            // firestoreに保存
            await updateDoc(docRef, image);

            setErrorMsg("Submission completed!");
        } catch(e){
        
            setErrorMsg(`Error: ${e}`);
        }
    };

    return(
        <div>
            <form>
                <input type="file" onChange={handleImageSelect} />
                <br />
                <input 
                    type="text" 
                    value={text} 
                    onChange={(e)=>{
                        setText(e.target.value);
                        setErrorMsg(null);
                    }}
                />
                <br />
                <button 
                    style={{cursor: "pointer", border: "1px solid gray"}}
                    onClick={uploadImage}
                >
                    upload
                </button>
            </form>
            <p style={{color:"red"}}>{errorMsg && errorMsg}</p>
            {imagePreview && (
                <img 
                    src={imagePreview} 
                    style={{
                        width: "auto",
                        height: 200,
                        objectFit: "cover",
                    }} 
                    alt="preview"
                />
            )}
        </div>
    );
};

export default Post;