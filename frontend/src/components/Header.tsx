import React from 'react';
import './Header.css';

const Header: React.FC = () => {
    return (
        <header className="header">
            <div className="logo">MyApp</div>
            <nav className="nav-links">
                <a href="#home">Home</a>
                <a href="#about">About</a>
                <a href="#contact" className="cta">Contact</a>
            </nav>
        </header>
    );
};

export default Header;
