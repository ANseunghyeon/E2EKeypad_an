"use client";
// Page.jsx
import React from 'react';
import useSecureKeypad from '../hooks/useSecureKeypad';
import SecureKeypad from "../components/SecureKeypad";
import KeypadUserInput from "../components/KeypadUserInput.jsx";

export default function Page() {
  const { states, actions } = useSecureKeypad();

  if (states.isLoading) {
    return <div>...isLoading</div>
  }

  if (states.error) {
    return <div>{states.error}</div>
  }

  if (states.keypad === null) {
    return <div>No keypad available.</div>
  } else {
    return (
      <div>
        <KeypadUserInput userInput={states.userInput}/>
        <SecureKeypad keypad={states.keypad} onKeyPressed={actions.onKeyPressed}/>
      </div>
    );
  }
}
