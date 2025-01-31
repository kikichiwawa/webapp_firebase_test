import { Timestamp } from "firebase/firestore";

type Image = {
    filePath: string;
    text: string;
    timestamp: Timestamp;
    grayFilePath: string|null;
}

export default Image;