import React, { useEffect, useState } from 'react';
import './App.css';
import useImages from './hooks/useImages';
import Post from './components/Post';
import PreviewImage from './components/PreviewImage';
import Header from './components/Header';
import { onAuthStateChanged, User } from 'firebase/auth';
import { auth } from './lib/firebase';
import Login from './components/Login';
import SignUp from './components/SignUp';
import SignOutButton from './components/SignOutButton';


const App: React.FC = () => {
  const [user, setUser] = useState<User | null>(null);

  useEffect(() => {
    const unsubscribe = onAuthStateChanged(auth, (currentUser)=>{
      setUser(currentUser);
    });
    return () => unsubscribe();
  });
  const {allImages, imagesError, fetchImages} = useImages();
  return (
    <div className="App">
      <Header />
      {user ? (
        <div>
          <SignOutButton />
          <Post fetchImages={fetchImages}/>
          <h3>投稿一覧</h3>
          <p style={{color: "red"}}>{imagesError && imagesError}</p>
          <div className='container'>
            <div className='row'>
              {allImages.map((image, index) => (
                <div className='col-4' key={index}>
                  <PreviewImage image={image}/>
                </div>
              ))}
              
              <button
                    onClick={fetchImages}
                    style={{ marginTop: "10px", cursor: "pointer" }}
                  >
                    リロード
                  </button>
            </div>
          </div>
        </div>
      ):(
        <div>
          <Login/>
          <SignUp/>
        </div>
      )}

    </div>
  );
};

export default App;
