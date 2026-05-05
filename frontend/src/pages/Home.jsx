import Dashboard from "../components/Dashboard";
import ExpenseList from "../components/ExpenseList";
import SubscriptionList from "../components/SubscriptionList";

function Home() {
    return (
        <div className="max-w-6xl mx-auto space-y-6">

            {/* Page Title */}
            <h1 className="text-3xl font-bold text-gray-800">
                Dashboard
            </h1>

            {/* Dashboard Summary (top cards) */}
            <Dashboard />

            {/* Lists Section */}
            <div className="grid grid-cols-1 md:grid-cols-2 gap-6">

                {/* Expenses */}
                <div className="bg-white p-4 rounded-xl shadow">
                    <h2 className="text-lg font-semibold mb-4">
                        Recent Expenses
                    </h2>
                    <ExpenseList />
                </div>

                {/* Subscriptions */}
                <div className="bg-white p-4 rounded-xl shadow">
                    <h2 className="text-lg font-semibold mb-4">
                        Subscriptions
                    </h2>
                    <SubscriptionList />
                </div>

            </div>
        </div>
    );
}

export default Home;