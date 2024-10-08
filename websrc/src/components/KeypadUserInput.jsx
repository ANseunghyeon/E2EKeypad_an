// KeypadUserInput.jsx

import React from 'react';
import '../style/keypad.css';

export default function KeypadUserInput({ userInput }) {
    return (
        <>
            <div className="input-group-style">
                {userInput.map((input, index) => (
                    <div key={index} className="input-char-style active" />
                ))}
            </div>
        </>
    );
}
