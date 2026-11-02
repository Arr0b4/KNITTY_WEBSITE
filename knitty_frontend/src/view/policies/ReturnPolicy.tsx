import "../../../styles/Policy.css";

export default function ReturnPolicy() {
	return (
		<section className="policy" aria-labelledby="returnTitle">
			<div className="policy__content">
				<header className="policy__header">
					<h1 id="returnTitle" className="policy__title">
						Return Policy
					</h1>
				</header>

				<section className="policy__section" aria-labelledby="returnOverview">
					<h2 id="returnOverview" className="policy__h2">
						Overview
					</h2>

					<p className="policy__text">
						At Knitty, we want you to feel confident and happy with every purchase.

Due to the digital nature of our knitting patterns, all sales are final once the pattern has been downloaded. Once you have accessed or downloaded your digital pattern, we are unable to offer refunds, returns, or exchanges.

By completing your purchase and downloading the pattern, you acknowledge and agree to these terms, subject to your applicable consumer rights.
					</p>
				</section>

		

			
				<section className="policy__section" aria-labelledby="returnRefunds">
					<h2 id="returnRefunds" className="policy__h2">
						Refunds
					</h2>

					<p className="policy__text">
						Approved refunds are issued to the original payment method after the
						returned item has been received, inspected, and approved.
					</p>

					<p className="policy__text">
						Please allow up to 7–10 business days after inspection for Knitty to
						process an approved refund. Your bank or payment provider may
						require additional time before the refund appears in your account.
					</p>

					<p className="policy__text">
						Submission or approval of a return request does not mean that a
						refund has already been issued. Refunds are processed only after the
						applicable return review and inspection process has been completed.
					</p>
				</section>

				<section className="policy__section" aria-labelledby="returnQuestions">
					<h2 id="returnQuestions" className="policy__h2">
						Questions
					</h2>

					<p className="policy__text">
						If you have questions about sizing, customization,
						status, or return eligibility, please contact the seller before placing your
						order.
					</p>

					<p className="policy__text">
						Email:{" "}
						<a
							className="policy__link"
							href="mailto:support@knitty.com"
						>
							support@knitty.com
						</a>
					</p>
				</section>
			</div>
		</section>
	);
}
