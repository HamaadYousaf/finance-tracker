import { useState } from "react";
import { useNavigate, Link } from "react-router-dom";

function Register() {
    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");

    const [loading, setLoading] = useState(false);
    const [error, setError] = useState("");

    const navigate = useNavigate();

    const handleRegister = async (e) => {
        e.preventDefault();

        setLoading(true);
        setError("");

        try {
            const res = await fetch("http://localhost:8080/api/auth/register", {
                method: "POST",
                headers: {
                    "Content-Type": "application/json",
                },
                body: JSON.stringify({
                    email,
                    password,
                }),
            });

            if (!res.ok) {
                throw new Error("Registration failed");
            }

            const data = await res.json();

            // store userId after registration
            localStorage.setItem("userId", data.id);
            localStorage.setItem("isLoggedIn", "true");

            navigate("/");
        } catch (err) {
            setError(err.message);
        } finally {
            setLoading(false);
        }
    };

    return (
        <div className="flex justify-center items-center min-h-[70vh]">
            <form
                onSubmit={handleRegister}
                className="bg-white p-6 rounded-xl shadow-md w-full max-w-md space-y-4"
            >
                <h2 className="text-2xl font-bold text-center">Register</h2>

                <input
                    type="email"
                    placeholder="Email"
                    className="w-full border p-2 rounded"
                    value={email}
                    onChange={(e) => setEmail(e.target.value)}
                    required
                />

                <input
                    type="password"
                    placeholder="Password"
                    className="w-full border p-2 rounded"
                    value={password}
                    onChange={(e) => setPassword(e.target.value)}
                    required
                />

                {error && (
                    <p className="text-red-500 text-sm text-center">{error}</p>
                )}

                <button
                    type="submit"
                    disabled={loading}
                    className="w-full bg-green-500 text-white p-2 rounded hover:bg-green-600"
                >
                    {loading ? "Creating account..." : "Register"}
                </button>

                <p className="text-sm text-center">
                    Already have an account?{" "}
                    <Link to="/login" className="text-blue-500">
                        Login
                    </Link>
                </p>
            </form>
        </div>
    );
}

export default Register;