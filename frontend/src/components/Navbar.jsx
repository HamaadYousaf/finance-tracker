import { Link } from "react-router-dom";

function Navbar() {
    return (
        <nav className="bg-white shadow-md">
            <div className="max-w-6xl mx-auto px-6 py-4 flex justify-between items-center">

                {/* Logo / Title */}
                <Link to="/" className="text-xl font-bold text-gray-800">
                    Finance Dashboard
                </Link>

                {/* Links */}
                <div className="space-x-6">
                    <Link
                        to="/"
                        className="text-gray-600 hover:text-blue-500 transition"
                    >
                        Dashboard
                    </Link>

                    <Link
                        to="/login"
                        className="bg-blue-500 text-white px-4 py-2 rounded hover:bg-blue-600 transition"
                    >
                        Login
                    </Link>
                </div>

            </div>
        </nav>
    );
}

export default Navbar;