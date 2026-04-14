import { useState } from "react";

export function useAuth() {
    const [isLoggedIn, setIsLoggedIn] = useState(
        localStorage.getItem("auth") === "true"
    );

    const login = () => setIsLoggedIn(true);

    return { isLoggedIn, login };
}