import type React from "react";
import { useEffect, useState } from "react";
import { FiMoon, FiSun } from "react-icons/fi";
import "../../styles/ThemeToggle.css";

const THEME_STORAGE_KEY = "knitty_theme";

export default function ThemeToggle(): React.ReactElement {
	const [theme, setTheme] = useState<"light" | "dark">(() => {
		const saved =
			localStorage.getItem(THEME_STORAGE_KEY) ||
			localStorage.getItem("tresse_theme");
		if (saved === "dark" || saved === "light") {
			return saved;
		}
		if (
			typeof window !== "undefined" &&
			window.matchMedia?.("(prefers-color-scheme: dark)").matches
		) {
			return "dark";
		}
		return "light";
	});

	useEffect(() => {
		document.documentElement.setAttribute("data-theme", theme);
		localStorage.setItem(THEME_STORAGE_KEY, theme);
		window.dispatchEvent(
			new CustomEvent("knitty:themeChanged", { detail: theme }),
		);
	}, [theme]);

	const toggleTheme = () => {
		setTheme((prev) => (prev === "dark" ? "light" : "dark"));
	};

	return (
		<button
			type="button"
			className="theme-toggle-btn"
			onClick={toggleTheme}
			aria-label={
				theme === "dark" ? "Switch to light mode" : "Switch to dark mode"
			}
			title={theme === "dark" ? "Switch to light mode" : "Switch to dark mode"}
		>
			{theme === "dark" ? (
				<FiSun className="theme-toggle-icon" size={20} aria-hidden="true" />
			) : (
				<FiMoon className="theme-toggle-icon" size={20} aria-hidden="true" />
			)}
		</button>
	);
}

