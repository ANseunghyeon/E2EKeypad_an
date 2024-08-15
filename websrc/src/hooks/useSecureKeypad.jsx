"use client";
// useSecureKeypad.jsx
import { useState, useEffect } from 'react';
import axios from "axios";

export default function useSecureKeypad() {
  const [keypad, setKeypad] = useState(null);
  const [userInput, setUserInput] = useState([]);
  const [isLoading, setIsLoading] = useState(true);
  const [error, setError] = useState('');

  useEffect(() => {
    getSecureKeypad();
  }, []);

  const getSecureKeypad = async () => {
    setIsLoading(true);
    try {
      const response = await axios.get('http://localhost:8080/api/keypad?id=1234');
      setKeypad({
        id: response.data.id,
        images: response.data.base64Images,
        hashes: response.data.hash
      });
      setIsLoading(false);
    } catch (error) {
      console.error('Failed to fetch keypad', error);
      setError('Failed to load keypad. Please try again later.');
      setIsLoading(false);
    }
  };

  const onKeyPressed = (hash) => {
    setUserInput([...userInput, hash]);
    if (userInput.length === 5) {
      alert(JSON.stringify(userInput));
      setUserInput([]); // Reset after showing
    }
  };

  return {
    states: {
      keypad,
      userInput,
      isLoading,
      error
    },
    actions: {
      onKeyPressed
    }
  };
}
