import React from 'react';
import '../style/keypad.css';

export default function SecureKeypad({ keypad, onKeyPressed }) {
    const { images, hashes } = keypad;
    const numRows = 3;
    const numCols = 4;
    const buttonWidth = 60; 
    const buttonHeight = 60; 

    return (
        <>
            <table className="table-style">
                <tbody>
                    {Array.from({ length: numRows }).map((_, rowIndex) => (
                        <tr key={rowIndex}>
                            {Array.from({ length: numCols }).map((_, colIndex) => {
                                const keyIndex = rowIndex * numCols + colIndex;
                                const hash = hashes[keyIndex];
                                if (!hash) return (
                                    <td key = {colIndex}>

                                    </td>
                                ); 
                                const xPos = -(colIndex * buttonWidth);
                                const yPos = -(rowIndex * buttonHeight);
                                return (
                                    <td key={colIndex}>
                                        <button
                                            type="button"
                                            className={"button-style"}
                                            onClick={() => onKeyPressed(hash)}
                                            style={{ 
                                                backgroundImage: `url(data:image/png;base64,${images})`,
                                                backgroundPosition: `${xPos}px ${yPos}px`
                                            }}
                                        >
                                        </button>
                                    </td>
                                );
                            })}
                        </tr>
                    ))}
                </tbody>
            </table>
        </>
    );
}
