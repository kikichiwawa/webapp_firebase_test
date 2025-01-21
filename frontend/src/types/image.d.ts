import { Timestamp } from "firebase/firestore";

type Image = {
    filePath: string;
    text: string;
    timestamp: Timestamp;
    greyFilePath: string|null;
}

export default Image;