import { Link, useNavigate } from "react-router-dom";

function Navbar() {
    const navigate = useNavigate();
    const isLoggedIn = localStorage.getItem("isLoggedIn");

    const handleLogout = () => {
        localStorage.removeItem("isLoggedIn");
        localStorage.removeItem("userId");
        navigate("/login");
    };

    return (
        <nav className="bg-white shadow-md">
            <div className="max-w-6xl mx-auto px-6 py-4 flex justify-between items-center">

                <Link to="/" className="text-xl font-bold text-gray-800">
                    Finance Dashboard
                </Link>

                <div className="flex items-center space-x-6">

                    {isLoggedIn ? (
                        <>
                            <Link to="/" className="hover:text-blue-500">
                                Dashboard
                            </Link>

                            <button
                                onClick={handleLogout}
                                className="text-red-500 hover:text-red-700"
                            >
                                Logout
                            </button>
                        </>
                    ) : (
                        <>
                            <Link to="/login" className="text-blue-500">
                                Login
                            </Link>

                            <Link to="/register" className="text-green-500">
                                Register
                            </Link>
                        </>
                    )}

                </div>
            </div>
        </nav>
    );
}

export default Navbar;