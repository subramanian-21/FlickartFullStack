import { render, screen } from "@testing-library/react";
import Header from "./components/Header";
import { Provider } from "react-redux";
import storeApp from "./utils/redux/store";
import CartBody from "./components/CartBody";
import "@testing-library/jest-dom"
test("testing heading component", () => {
  render(
    <Provider store={storeApp}>
      <CartBody />
    </Provider>
  );
    const heading = screen.getByRole("button")
expect(heading).toBeInTheDocument()
});

